package Commands.School;

import Commands.SchoolCmd;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;

public class Chem extends SchoolCmd {

    public Chem() {
        this.name = "chem";
        this.help = "does chemistry calculations";
    }

    @Override
    public void doCommand(CommandEvent e) {
        String msg = e.getMessage().getContentRaw();
        String law = determineLaw(msg);
        String answer = " ";
        switch (law) {
            case "boyle":
                answer = boyleLaw(parseString(msg, law));
                break;
            case "personStoopid":
                answer = "You are stoopid cuz you missed a variable smh";
                break;
            case "gayLussac":
                answer = gayLussac(parseString(msg, law));
                break;
            case "charles":
                answer = charles(parseString(msg, law));
                break;
            case "combinedGas":
                answer = combinedGas(parseString(msg, law));
                break;
        }
        if(!answer.isEmpty()) {
            e.replySuccess("Answer: " + answer);
        } else {
            e.replyError("Oop it didnt work");
        }
    }

    String determineLaw(String equation) {
        String law = " ";

        boolean pressure = false;
        boolean volume = false;
        boolean temperature = false;

        String[] items = equation.split("\\s+");
        for (String number : items) {
            if(number.toLowerCase().contains("kpa")) {
                pressure = true;
            }
            if(number.toLowerCase().contains("ml") || number.contains("L")) {
                volume = true;
            }
            if(number.toLowerCase().contains("c")) {
                temperature = true;
            }
        }

        if(pressure && volume && temperature) {
            law = "combinedGas";
        } else if(pressure && volume) {
            law = "boyle";
        } else if(pressure && temperature) {
            law = "gayLussac";
        } else if(volume && temperature) {
            law = "charles";
        } else {
            law = "personStoopid";
        }
        return law;
    }

    double[] parseString(String msg, String law) {
        double kpa1 = 0;
        double kpa2 = 0;
        Volume volume1 = new Volume();
        Volume volume2 = new Volume();
        double volumeMeasurement = 0;
        double temp1 = -1000;
        double temp2 = -1000;
        double[] allNums;

        String[] items = msg.split("\\s+");
        for (String x : items) {
            if(x.toLowerCase().contains("satp1")) {
                kpa1 = 100;
                temp1 = 25 + 273;
            } else if(x.toLowerCase().contains("satp2")) {
                kpa2 = 100;
                temp2 = 25 + 273;
            } else if(x.toLowerCase().contains("stp1")) {
                kpa1 = 100;
                temp1 = 273;
            } else if(x.toLowerCase().contains("stp2")) {
                kpa2 = 100;
                temp2 = 273;
            }

            if(!law.equals("charles")) {
                if(x.toLowerCase().contains("kpa1")) {
                    kpa1 = Double.parseDouble(deleteMeasurements(x, "kpa1"));
                } else if(x.toLowerCase().contains("kpa2")) {
                    kpa2 = Double.parseDouble(deleteMeasurements(x, "kpa2"));
                }
            }
            if(!law.equals("gayLussac")) {
                if(x.toLowerCase().contains("ml1")) {
                    volume1.setNum(Double.parseDouble(deleteMeasurements(x, "ml1")));
                    volume1.setMeasurements("mL");
                } else if(x.contains("L1")) {
                    volume1.setNum(Double.parseDouble(deleteMeasurements(x, "l1")));
                    volume1.setMeasurements("L");
                } else if(x.toLowerCase().contains("ml2")) {
                    volume2.setNum(Double.parseDouble(deleteMeasurements(x, "ml2")));
                    volume2.setMeasurements("mL");
                } else if(x.contains("L2")) {
                    volume2.setNum(Double.parseDouble(deleteMeasurements(x, "l2")));
                    volume2.setMeasurements("L");
                }

                if(!volume1.getMeasurements().contains(volume2.getMeasurements())) {
                    if(volume1.getMeasurements().equals("mL")) {
                        double tempNum = volume2.getNum();
                        volume2.setNum(tempNum / 1000);
                        volume2.setMeasurements("mL");
                    } else if(volume2.getMeasurements().equals("mL")) {
                        double tempNum = volume1.getNum();
                        volume1.setNum(tempNum / 1000);
                        volume1.setMeasurements("mL");
                    }
                    volumeMeasurement = volumeMeasurement(volume2.getMeasurements());
                }
            }
            if(!law.equals("boyle")) {
                if(x.toLowerCase().contains("c1")) {
                    temp1 = Double.parseDouble(deleteMeasurements(x, "c1")) + 273; // Convert to Kelvin
                }
                if(x.toLowerCase().contains("c2")) {
                    temp2 = Double.parseDouble(deleteMeasurements(x, "c2")) + 273; // Convert to Kelvin
                }
            }
        }

        switch (law) {
            case "boyle":
                allNums = new double[]{kpa1, kpa2, volume1.getNum(), volume2.getNum(), volumeMeasurement};
                break;
            case "charles":
                allNums = new double[]{volume1.getNum(), volume2.getNum(), temp1, temp2, volumeMeasurement};
                break;
            case "gayLussac":
                allNums = new double[]{kpa1, kpa2, temp1, temp2};
                break;
            case "combinedGas":
                allNums = new double[]{kpa1, kpa2, volume1.getNum(), volume2.getNum(), temp1, temp2, volumeMeasurement};
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + law);
        }
        return allNums;
    }

    String boyleLaw(double[] allNums) {
        double kpa1 = allNums[0];
        double kpa2 = allNums[1];
        double volume1 = allNums[2];
        double volume2 = allNums[3];
        double answer;
        String measurements = volumeMeasurement(allNums[4]);
        double[] finalNums;

        if(kpa1 == 0) {
            answer = kpa2 * volume2 / volume1;
            finalNums = new double[]{kpa2, volume1, volume2};
            measurements = "kPa";
        } else if(kpa2 == 0) {
            answer = kpa1 * volume1 / volume2;
            finalNums = new double[]{kpa1, volume1, volume2};
            measurements = "kPa";
        } else if(volume1 == 0) {
            answer = kpa2 * volume2 / kpa1;
            finalNums = new double[]{kpa1, kpa2, volume2};
        } else {
            answer = kpa1 * volume1 / kpa2;
            finalNums = new double[]{kpa1, kpa2, volume1};
        }
        return sigDigs(finalNums, answer) + measurements;
    }

    String charles(double[] allNums) {
        double volume1 = allNums[0];
        double volume2 = allNums[1];
        double temp1 = allNums[2];
        double temp2 = allNums[3];
        String measurements = volumeMeasurement(allNums[4]);
        double answer;
        double[] finalNums;

        if(volume1 == 0) {
            answer = (volume2 / temp2) * temp1;
            finalNums = new double[]{volume2, temp1, temp2};
        } else if(volume2 == 0) {
            answer = (volume1 / temp1) * temp2;
            finalNums = new double[]{volume1, temp1, temp2};
        } else if(temp1 == -1000) {
            answer = volume1 / (volume2 / temp2) - 273;
            finalNums = new double[]{volume1, volume2, temp2};
            measurements = "°C";
        } else {
            answer = volume2 / (volume1 / temp1) - 273;
            finalNums = new double[]{volume1, volume2, temp1};
            measurements = "°C";
        }

        return sigDigs(finalNums, answer) + measurements;
    }

    String gayLussac(double[] allNums) {
        double kpa1 = allNums[0];
        double kpa2 = allNums[1];
        double temp1 = allNums[2];
        double temp2 = allNums[3];
        String measurements;
        double answer;
        double[] finalNums;

        if(kpa1 == 0) {
            answer = (kpa2 / temp2) * temp1;
            measurements = "kPa";
            finalNums = new double[]{kpa2, temp1, temp2};
        } else if(kpa2 == 0) {
            answer = (kpa1 / temp1) * temp2;
            measurements = "kPa";
            finalNums = new double[]{kpa1, temp1, temp2};
        } else if(temp1 == -1000) {
            answer = kpa1 / (kpa2 / temp2) - 273;
            measurements = "°C";
            finalNums = new double[]{kpa2, kpa1, temp2};
        } else {
            answer = kpa2 / (kpa1 / temp1) - 273;
            measurements = "°C";
            finalNums = new double[]{kpa2, kpa1, temp1};
        }

        return sigDigs(finalNums, answer) + measurements;
    }

    String combinedGas(double[] allNums) {
        double kpa1 = allNums[0];
        double kpa2 = allNums[1];
        double volume1 = allNums[2];
        double volume2 = allNums[3];
        double temp1 = allNums[4];
        double temp2 = allNums[5];
        double answer;
        double[] finalNums;
        String measurements = volumeMeasurement(allNums[6]);

        if(kpa1 == 0) {
            answer = ((kpa2 * volume2) / temp2) * temp1 / volume1;
            measurements = "kPa";
            finalNums = new double[]{kpa2, volume1, volume2, temp1, temp2};
        } else if(kpa2 == 0) {
            answer = ((kpa1 * volume1) / temp1) * temp2 / volume2;
            measurements = "kPa";
            finalNums = new double[]{kpa1, volume1, volume2, temp1, temp2};
        } else if(volume1 == 0) {
            answer = ((kpa2 * volume2) / temp2) * temp1 / kpa1;
            finalNums = new double[]{kpa1, kpa2, volume2, temp1, temp2};
        } else if(volume2 == 0) {
            answer = ((kpa1 * volume1) / temp1) * temp2 / kpa2;
            finalNums = new double[]{kpa1, kpa2, volume1, temp1, temp2};
        } else if(temp1 == -1000) {
            answer = (kpa1 * volume1) / (kpa2 * volume2 / temp2) - 273;
            finalNums = new double[]{kpa1, kpa2, volume1, volume2, temp2};
            measurements = "°C";
        } else {
            answer = (kpa2 * volume2) / (kpa1 * volume1 / temp1) - 273;
            finalNums = new double[]{kpa1, kpa2, volume1, volume2, temp1};
            measurements = "°C";
        }

        return sigDigs(finalNums, answer) + measurements;
    }

    String deleteMeasurements(String msg, String toDelete) {
        return msg.toLowerCase().replace(toDelete, "");
    }

    String sigDigs(double[] numbers, double answer) {
        int sigDigs = String.valueOf(numbers[0]).length();

        for (double x : numbers) {
            BigDecimal bd = new BigDecimal(String.valueOf(x));
            int digits = getSigDigs(bd);

            if(sigDigs > digits) {
                sigDigs = digits;
            }
        }

        BigDecimal bd = new BigDecimal(answer);
        bd = bd.round(new MathContext(sigDigs));

        return bd.toString();
    }

    int volumeMeasurement(String measurement) {
        if(measurement.contains("mL")) {
            return -1;
        } else {
            return -2;
        }
    }

    String volumeMeasurement(double measurement) {
        if(measurement == -1) {
            return "mL";
        } else if(measurement == -2) {
            return "L";
        } else {
            return "";
        }
    }

    int getSigDigs(BigDecimal input) {
        return input.scale() < 0
                ? input.precision() - input.scale()
                : input.precision();
    }

}

class Volume {

    double num;
    String measurements;

    Volume() {
        this.num = 0;
        this.measurements = "ml";
    }

    public double getNum() {
        return num;
    }

    public String getMeasurements() {
        return measurements;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public void setMeasurements(String measurements) {
        this.measurements = measurements;
    }
}
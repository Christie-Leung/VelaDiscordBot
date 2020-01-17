package Commands.Utilities.MovieList;

public class MovieListImpl implements MovieList {
    private int position;
    private String movie;

    public MovieListImpl() {
    }

    public MovieListImpl(String[] args) {
        if(args.length != 2) return;
        setPosition(Integer.parseInt(args[0]));
        setMovie(args[1]);
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String getMovie() {
        return movie;
    }

    @Override
    public void setMovie(String movie) {
        this.movie = movie;
    }
}

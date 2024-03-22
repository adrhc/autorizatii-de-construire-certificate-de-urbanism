package adrhc.go.ro.constructionauth.datasource.index;

public record MonthYear(int year, int month) implements Comparable<MonthYear> {
    public static MonthYear parse(String monthYear) {
        String[] strings = monthYear.split(":");
        return new MonthYear(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
    }

    @Override
    public int compareTo(MonthYear o) {
        if (year != o.year) {
            return year > o.year ? 1 : -1;
        } else if (month != o.month) {
            return month > o.month ? 1 : -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return year + ":" + month;
    }
}

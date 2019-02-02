package newbilius.GamesRevival.HTML.Breadcrumbs;

import java.util.ArrayList;

public class BreadcrumbGenerator {
    private ArrayList<Breadcrumb> breadcrumbs = new ArrayList<>();

    public void add(String title, String url) {
        breadcrumbs.add(new Breadcrumb(title, url));
    }

    public void add(String title) {
        breadcrumbs.add(new Breadcrumb(title, ""));
    }

    public String generate() {
        var stringBuilder = new StringBuilder();

        stringBuilder.append("<nav aria-label='breadcrumb'><ol class='breadcrumb'>");

        for (var i = 0; i < breadcrumbs.size() - 1; i++) {
            var breadcrumb = breadcrumbs.get(i);
            stringBuilder.append(String.format("<li class='breadcrumb-item'><a href='%s'>%s</a></li>",
                    breadcrumb.Url,
                    breadcrumb.Title));
        }

        stringBuilder.append(String.format("<li class='breadcrumb-item active' aria-current='page'>%s</li>",
                breadcrumbs.get(breadcrumbs.size() - 1).Title));

        stringBuilder.append("</ol></nav>");

        return stringBuilder.toString();
    }
}

class Breadcrumb {
    String Title;
    final String Url;

    Breadcrumb(String title, String url) {
        Title = title;
        Url = url;
    }
}

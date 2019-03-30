package newbilius.GamesRevival.HTML.Badges;

public class BadgeGenerator {
    public static String createBadge(String tag) {
        tag = tag.toLowerCase().trim();
        return BadgeGenerator.createBadge(tag, getBadgeType(tag));
    }

    private static BadgeType getBadgeType(String tag) {
        var type = BadgeType.Blue;
        if (tag.contains("не требует оригинальной игры"))
            type = BadgeType.Green2;
        if (tag.contains("альфа-версия"))
            type = BadgeType.Yellow;
        if (tag.contains("бета-версия"))
            type = BadgeType.Yellow;
        if (tag.contains("заброшен"))
            type = BadgeType.Red;
        if (tag.contains("неиграбелен"))
            type = BadgeType.Red;
        if (tag.contains("sourceport"))
            type = BadgeType.Blue;
        if (tag.contains("mod"))
            type = BadgeType.Green;
        if (tag.contains("официальный remaster"))
            type = BadgeType.Black;
        return type;
    }

    public static String createBadge(String text, BadgeType type) {
        return String.format("<span class='badge %s'>%s</span> ",
                getBadgeColor(type),
                text);
    }

    private static String getBadgeColor(BadgeType type) {
        switch (type) {
            case Blue:
                return "badge-primary";
            case Grey:
                return "badge-secondary";
            case Green:
                return "badge-success";
            case Red:
                return "badge-danger";
            case Yellow:
                return "badge-warning";
            case Green2:
                return "badge-info";
            case Black:
                return "badge-dark";
        }
        return "badge-primary";
    }
}

package newbilius.GamesRevival.HTML;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Collections;
import java.util.Set;

public class MDToHtmlConverter {

    private final Parser parser;
    private final HtmlRenderer renderer;

    public MDToHtmlConverter() {
        Set<Extension> extensions = Collections.singleton(TablesExtension.create());
        renderer = HtmlRenderer
                .builder()
                .extensions(extensions)
                .build();
        parser = Parser.builder().extensions(extensions).build();
    }

    public String convert(String text) {
        Node document = parser.parse(text);
        return renderer.render(document);
    }
}

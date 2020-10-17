package ru.dorokhov.sd.refactoring.response;

public class ResponseBuilder {
    private final StringBuilder response;

    public ResponseBuilder() {
        this.response = new StringBuilder("<html><body>\n");
    }

    public ResponseBuilder addH1Title(final String title) {
        final String h1Title = String.format("<h1>%s</h1>\n", title);
        response.append(h1Title);
        return this;
    }

    public ResponseBuilder addTitle(final String title) {
        response.append(title);
        response.append("\n");
        return this;
    }

    public ResponseBuilder addElement(final String element) {
        response.append(element);
        response.append("<br>\n");
        return this;
    }

    public String build() {
        response.append("</body></html>\n");
        return response.toString();
    }
}

package lexAnalyzer;

/**
 * Token class
 * @author cuihao
 */
public class Token {
    /**
     * catalog of this token
     * {@link Catalog}
     */
    private Catalog catalog;
    /**
     * certain lexeme
     */
    private String lexeme;
    /**
     * type code
     */
    private int index;

    public Token(Catalog catalog, String lexeme, int index) {
        this.catalog = catalog;
        this.lexeme = lexeme;
        this.index = index;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        if (index>=0) {
            return "<"+catalog+"_"+index+","+lexeme+">";
        }
        return "<"+catalog+","+lexeme+">";
    }
}

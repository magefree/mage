package mage.cards.repository;

/**
 * Token item for tokens database
 *
 * @author JayDi85
 */
public class TokenInfo {

    private TokenType tokenType;
    private String name;
    private String setCode;
    private Integer imageNumber = 1; // if one set contains diff images with same name

    private String classFileName;

    private String imageFileName;

    public TokenInfo(TokenType tokenType, String name, String setCode, Integer imageNumber) {
        this(tokenType, name, setCode, imageNumber, "", "");
    }

    public TokenInfo(TokenType tokenType, String name, String setCode, Integer imageNumber, String classFileName, String imageFileName) {
        this.tokenType = tokenType;
        this.name = name;
        this.setCode = setCode;
        this.imageNumber = imageNumber;
        this.classFileName = classFileName;
        this.imageFileName = imageFileName;
    }

    @Override
    public String toString() {
        return String.format("%s - %s - %d (%s)", this.setCode, this.name, this.imageNumber, this.classFileName);
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getName() {
        return name;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public String getSetCode() {
        return setCode;
    }

    public String getClassFileName() {
        return classFileName;
    }

    public Integer getImageNumber() {
        return imageNumber;
    }

    public String getFullClassFileName() {
        String simpleName = classFileName.isEmpty() ? name.replaceAll("[^a-zA-Z0-9]", "") : classFileName;
        switch (this.tokenType) {
            case TOKEN:
                return "mage.game.permanent.token." + simpleName;
            case EMBLEM:
                return "mage.game.command.emblems." + simpleName;
            case PLANE:
                return "mage.game.command.planes." + simpleName;
            case DUNGEON:
                return "mage.game.command.dungeons." + simpleName;
            default:
                throw new IllegalStateException("Unknown token type: " + this.tokenType);
        }
    }
}

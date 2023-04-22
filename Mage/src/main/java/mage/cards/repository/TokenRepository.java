package mage.cards.repository;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author JayDi85
 */
public enum TokenRepository {

    instance;

    private static final Logger logger = Logger.getLogger(TokenRepository.class);

    private ArrayList<TokenInfo> allTokens = new ArrayList<>();
    private Map<String, List<TokenInfo>> indexByClassName = new HashMap<>();
    private Map<TokenType, List<TokenInfo>> indexByType = new HashMap<>();

    TokenRepository() {
    }

    public void init() {
        if (!allTokens.isEmpty()) {
            return;
        }

        allTokens = loadAllTokens();

        // index
        allTokens.forEach(token -> {
            // by class
            String needClass = token.getFullClassFileName();
            List<TokenInfo> list = indexByClassName.getOrDefault(needClass, null);
            if (list == null) {
                list = new ArrayList<>();
                indexByClassName.put(needClass, list);
            }
            list.add(token);

            // by type
            list = indexByType.getOrDefault(token.getTokenType(), null);
            if (list == null) {
                list = new ArrayList<>();
                indexByType.put(token.getTokenType(), list);
            }
            list.add(token);
        });
    }

    public List<TokenInfo> getAll() {
        init();
        return allTokens;
    }

    public Map<String, List<TokenInfo>> getAllByClassName() {
        init();
        return indexByClassName;
    }

    public List<TokenInfo> getByType(TokenType tokenType) {
        init();
        return indexByType.getOrDefault(tokenType, new ArrayList<>());
    }

    public List<TokenInfo> getByClassName(String fullClassName) {
        init();
        return indexByClassName.getOrDefault(fullClassName, new ArrayList<>());
    }

    private static ArrayList<TokenInfo> loadAllTokens() throws RuntimeException {
        // Must load tokens data in strict mode (throw exception on any error)
        // Try to put verify checks here instead verify tests
        String dbSource = "tokens-database.txt";
        ArrayList<TokenInfo> list = new ArrayList<>();
        InputStream in = TokenRepository.class.getClassLoader().getResourceAsStream(dbSource);
        if (in == null) {
            throw new RuntimeException("Tokens database: can't load resource file " + dbSource);
        }

        List<String> errorsList = new ArrayList<>();
        try (InputStreamReader input = new InputStreamReader(in);
             BufferedReader reader = new BufferedReader(input)) {
            String line = reader.readLine();
            while (line != null) {
                try {
                    line = line.trim();
                    if (!line.startsWith("|")) {
                        continue;
                    }

                    List<String> params = Arrays.stream(line.split("\\|", -1))
                            .map(String::trim)
                            .collect(Collectors.toList());
                    if (params.size() < 5) {
                        errorsList.add("Tokens database: wrong params count: " + line);
                        continue;
                    }
                    if (!params.get(1).toLowerCase(Locale.ENGLISH).equals("generate")) {
                        // TODO: remove "generate" from db
                        errorsList.add("Tokens database: miss generate param: " + line);
                        continue;
                    }

                    // image number (uses if one set contains multiple tokens with same name)
                    int imageNumber = 0;
                    if (!params.get(4).isEmpty()) {
                        imageNumber = Integer.parseInt(params.get(4));
                    }

                    // image file name
                    String imageFileName = "";
                    if (params.size() > 5 && !params.get(5).isEmpty()) {
                        imageFileName = params.get(5);
                    }

                    // token class name (uses for images search for render)
                    String tokenClassName = "";
                    if (params.size() > 7 && !params.get(6).isEmpty()) {
                        tokenClassName = params.get(6);
                    }
                    if (tokenClassName.isEmpty()) {
                        errorsList.add("Tokens database: miss class name: " + line);
                        continue;
                    }

                    // object type
                    String objectType = params.get(2);
                    String tokenName = params.get(3);
                    String setCode = "";
                    TokenType tokenType = null;

                    // type - token
                    if (objectType.startsWith("TOK:")) {
                        setCode = objectType.substring("TOK:".length());
                        tokenType = TokenType.TOKEN;
                    }

                    // type - emblem
                    if (objectType.startsWith("EMBLEM:")) {
                        setCode = objectType.substring("EMBLEM:".length());
                        tokenType = TokenType.EMBLEM;
                        if (!tokenName.startsWith("Emblem ")) {
                            errorsList.add("Tokens database: emblem's name must start with [Emblem ...] word: " + line);
                            continue;
                        }
                        if (!tokenClassName.endsWith("Emblem")) {
                            errorsList.add("Tokens database: emblem's class name must ends with [...Emblem] word: " + line);
                            continue;
                        }
                    }

                    // type - plane
                    if (objectType.startsWith("PLANE:")) {
                        setCode = objectType.substring("PLANE:".length());
                        tokenType = TokenType.PLANE;
                        if (!tokenName.startsWith("Plane - ")) {
                            errorsList.add("Tokens database: plane's name must start with [Plane - ...] word: " + line);
                            continue;
                        }
                        if (!tokenClassName.endsWith("Plane")) {
                            errorsList.add("Tokens database: plane's class name must ends with [...Plane] word: " + line);
                            continue;
                        }
                    }

                    // type - dungeon
                    if (objectType.startsWith("DUNGEON:")) {
                        setCode = objectType.substring("DUNGEON:".length());
                        tokenType = TokenType.DUNGEON;
                        if (!tokenClassName.endsWith("Dungeon")) {
                            errorsList.add("Tokens database: dungeon's class name must ends with [...Dungeon] word: " + line);
                            continue;
                        }
                    }

                    // type - unknown
                    if (tokenType == null) {
                        errorsList.add("Tokens database: unknown line format: " + line);
                        continue;
                    }

                    // OK
                    TokenInfo token = new TokenInfo(tokenType, tokenName, setCode, imageNumber, tokenClassName, imageFileName);
                    list.add(token);
                } finally {
                    line = reader.readLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Tokens database: can't read data, unknown error - " + e.getMessage());
        }

        if (!errorsList.isEmpty()) {
            errorsList.forEach(logger::error);
            throw new RuntimeException(String.format("Tokens database: found %d errors, see logs above for details", errorsList.size()));
        }

        return list;
    }
}

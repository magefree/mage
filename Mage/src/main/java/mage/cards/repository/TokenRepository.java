package mage.cards.repository;

import mage.util.RandomUtil;
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

    public static final String XMAGE_TOKENS_SET_CODE = "XMAGE";

    private static final Logger logger = Logger.getLogger(TokenRepository.class);

    private ArrayList<TokenInfo> allTokens = new ArrayList<>();
    private final Map<String, List<TokenInfo>> indexByClassName = new HashMap<>();
    private final Map<TokenType, List<TokenInfo>> indexByType = new HashMap<>();

    TokenRepository() {
    }

    public void init() {
        if (!allTokens.isEmpty()) {
            return;
        }

        // tokens
        allTokens = loadMtgTokens();
        allTokens.addAll(loadXmageTokens());

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

    private static ArrayList<TokenInfo> loadMtgTokens() throws RuntimeException {
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

    public Map<String, String> prepareScryfallDownloadList() {
        init();

        Map<String, String> res = new LinkedHashMap<>();

        // format example:
        // put("ONC/Angel/1", "https://api.scryfall.com/cards/tonc/2/en?format=image");
        allTokens.stream()
                .filter(token -> token.getTokenType().equals(TokenType.XMAGE))
                .forEach(token -> {
                    String code = String.format("%s/%s/%d", token.getSetCode(), token.getName(), token.getImageNumber());
                    res.put(code, token.getDownloadUrl());
                });
        return res;
    }

    private static TokenInfo createXmageToken(String name, Integer imageNumber, String scryfallDownloadUrl) {
        return new TokenInfo(TokenType.XMAGE, name, XMAGE_TOKENS_SET_CODE, imageNumber)
                .withDownloadUrl(scryfallDownloadUrl);
    }

    private static ArrayList<TokenInfo> loadXmageTokens() {
        // Create reminder/helper tokens (special images like Copy, Morph, Manifest, etc)
        // Search by
        // - https://tagger.scryfall.com/tags/card/assistant-cards
        // - https://scryfall.com/search?q=otag%3Aassistant-cards&unique=cards&as=grid&order=name
        // Must add only unique prints
        // TODO: add custom set in download window to download a custom tokens only
        // TODO: add custom set in card viewer to view a custom tokens only
        ArrayList<TokenInfo> res = new ArrayList<>();

        // Copy
        // https://scryfall.com/search?q=include%3Aextras+unique%3Aprints+type%3Atoken+copy&unique=cards&as=grid&order=name
        res.add(createXmageToken("Copy", 1, "https://api.scryfall.com/cards/tclb/19/en?format=image"));
        res.add(createXmageToken("Copy", 2, "https://api.scryfall.com/cards/tsnc/1/en?format=image"));
        res.add(createXmageToken("Copy", 3, "https://api.scryfall.com/cards/tvow/19/en?format=image"));
        res.add(createXmageToken("Copy", 4, "https://api.scryfall.com/cards/tznr/12/en?format=image"));

        // City's Blessing
        // https://scryfall.com/search?q=type%3Atoken+include%3Aextras+unique%3Aprints+City%27s+Blessing+&unique=cards&as=grid&order=name
        res.add(createXmageToken("City's Blessing", 1, "https://api.scryfall.com/cards/f18/2/en?format=image"));

        // Day // Night
        // https://scryfall.com/search?q=include%3Aextras+unique%3Aprints+%22Day+%2F%2F+Night%22&unique=cards&as=grid&order=name
        res.add(createXmageToken("Day", 1, "https://api.scryfall.com/cards/tvow/21/en?format=image&face=front"));
        res.add(createXmageToken("Night", 1, "https://api.scryfall.com/cards/tvow/21/en?format=image&face=back"));

        // Manifest
        // https://scryfall.com/search?q=Manifest+include%3Aextras+unique%3Aprints&unique=cards&as=grid&order=name
        res.add(createXmageToken("Manifest", 1, "https://api.scryfall.com/cards/tc19/28/en?format=image"));
        res.add(createXmageToken("Manifest", 2, "https://api.scryfall.com/cards/tc18/1/en?format=image"));
        res.add(createXmageToken("Manifest", 3, "https://api.scryfall.com/cards/tfrf/4/en?format=image"));
        res.add(createXmageToken("Manifest", 4, "https://api.scryfall.com/cards/tncc/3/en?format=image"));

        // Morph
        // https://scryfall.com/search?q=Morph+unique%3Aprints+otag%3Aassistant-cards&unique=cards&as=grid&order=name
        res.add(createXmageToken("Morph", 1, "https://api.scryfall.com/cards/tktk/11/en?format=image"));
        res.add(createXmageToken("Morph", 2, "https://api.scryfall.com/cards/ta25/15/en?format=image"));
        res.add(createXmageToken("Morph", 3, "https://api.scryfall.com/cards/tc19/27/en?format=image"));

        // The Monarch
        // https://scryfall.com/search?q=Monarch+unique%3Aprints+otag%3Aassistant-cards&unique=cards&as=grid&order=name
        res.add(createXmageToken("The Monarch", 1, "https://api.scryfall.com/cards/tonc/22/en?format=image"));
        res.add(createXmageToken("The Monarch", 2, "https://api.scryfall.com/cards/tcn2/1/en?format=image"));

        return res;
    }

    /**
     * Try to find random image info by related set code
     *
     * @param possibleList     all possible tokens e.g. by clas name
     * @param preferredSetCode primary set code for possible image (if not found then will use any set)
     * @return
     */
    private TokenInfo findPreferredTokenInfo(List<TokenInfo> possibleList, String preferredSetCode) {
        // search by set code
        List<TokenInfo> needList = possibleList.stream()
                .filter(info -> info.getSetCode().equals(preferredSetCode))
                .collect(Collectors.toList());

        // search by all sets
        if (needList.isEmpty()) {
            needList = possibleList;
        }

        // also will return diff image number for tokens
        if (needList.size() > 0) {
            return RandomUtil.randomFromCollection(needList);
        } else {
            return null;
        }
    }

    /**
     * Try to find random image info by related set code
     *
     * @param className        full class name of the token or other object
     * @param preferredSetCode primary set code for possible image (if not found then will use any set)
     * @return
     */
    public TokenInfo findPreferredTokenInfoForClass(String className, String preferredSetCode) {
        return findPreferredTokenInfo(TokenRepository.instance.getByClassName(className), preferredSetCode);
    }
}

package mage.game.draft;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.util.RandomUtil;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Data sources by priority:
 * - official mtgo website
 * - cubecobra (big amount of cubes but can be outdated)
 *
 * @author LevelX2, JayDi85
 */
public abstract class DraftCube {

    SimpleDateFormat UPDATE_DATE_FORMAT = new SimpleDateFormat("yyyy MMMM", Locale.ENGLISH); // 2025 April
    private static final Logger logger = Logger.getLogger(DraftCube.class);


    public static class CardIdentity {

        private final String name;
        private final String extension;
        /**
         * optional number in the extension (some sets have multiple version of a card)
         * null means no set one.
         */
        private final String number;

        public CardIdentity(String name, String extension) {
            this(name, extension, null);
        }

        public CardIdentity(String name, String extension, String number) {
            this.name = name;
            this.extension = extension;
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public String getExtension() {
            return extension;
        }

        public String getCardNumber() {
            return number;
        }
    }

    private final String name;
    private final String code;
    private String updateInfo;
    private final Date updateDate;
    private static final int boosterSize = 15;

    protected List<CardIdentity> cubeCards = new ArrayList<>();
    protected List<CardIdentity> leftCubeCards = new ArrayList<>();

    protected DraftCube(String name) {
        this(name, "", 0, 0, 0);
    }

    public DraftCube(String name, String updateInfo, int updateYear, int updateMonth, int updateDay) {
        this.name = name;
        this.code = getClass().getSimpleName();
        this.updateInfo = updateInfo;
        this.updateDate = updateYear == 0 ? null : ExpansionSet.buildDate(updateYear, updateMonth, updateDay);
    }

    public String getName() {
        String res = this.name;

        List<String> extra = new ArrayList<>();
        if (this.updateInfo != null && !this.updateInfo.isEmpty()) {
            extra.add(this.updateInfo);
        }
        if (this.updateDate != null) {
            extra.add(UPDATE_DATE_FORMAT.format(this.updateDate));
        }
        if (!extra.isEmpty()) {
            res += String.format(" (%s)", String.join(", ", extra));
        }

        return res;
    }

    /**
     * Validate inner data - is it fine to start (example: cube from deck must has loaded cards)
     */
    public void validateData() {
        if (cubeCards.isEmpty()) {
            throw new IllegalArgumentException("Can't create cube draft - found empty cards list: " + this.getName());
        }
    }

    public String getCode() {
        return code;
    }

    public String getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(String info) {
        this.updateInfo = info;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public List<CardIdentity> getCubeCards() {
        return cubeCards;
    }

    public List<Card> createBooster() {
        List<Card> booster = new ArrayList<>();
        if (leftCubeCards.isEmpty()) {
            leftCubeCards.addAll(cubeCards);
        }

        for (int i = 0; i < boosterSize; i++) {
            boolean done = false;
            int notValid = 0;
            while (!done) {
                int index = RandomUtil.nextInt(leftCubeCards.size());
                CardIdentity cardId = leftCubeCards.get(index);
                leftCubeCards.remove(index);
                if (!cardId.getName().isEmpty()) {
                    CardInfo cardInfo = null;
                    if (!cardId.getExtension().isEmpty()) {
                        cardInfo = CardRepository.instance.findCardWithPreferredSetAndNumber(
                                cardId.getName(), cardId.getExtension(), cardId.getCardNumber()
                        );
                    } else {
                        cardInfo = CardRepository.instance.findPreferredCoreExpansionCard(cardId.getName());
                    }

                    if (cardInfo != null) {
                        booster.add(cardInfo.createCard());
                        done = true;
                    } else {
                        logger.warn(new StringBuilder(this.getName()).append(" - Card not found: ").append(cardId.getName()).append(':').append(cardId.extension));
                        notValid++;
                    }
                } else {
                    logger.error(new StringBuilder(this.getName()).append(" - Empty card name: ").append(cardId.getName()).append(':').append(cardId.extension));
                    notValid++;
                }

                if (leftCubeCards.isEmpty()) {
                    leftCubeCards.addAll(cubeCards);
                }
                if (notValid > cubeCards.size()) {
                    logger.error(new StringBuilder(this.getName()).append(" - Booster could not be created, no valid cards found "));
                    done = true;
                }
            }
        }

        return booster;
    }

    void removeFromLeftCards(CardIdentity cardId) {
        if (cardId == null) {
            return;
        }

        for (int i = leftCubeCards.size() - 1; i >= 0; i--) {
            if (Objects.equals(leftCubeCards.get(i), cardId)) {
                leftCubeCards.remove(i);
                return;
            }
        }
    }
}

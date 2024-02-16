package mage.game.draft;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.util.RandomUtil;
import org.apache.log4j.Logger;

/**
 *
 * @author LevelX2
 */
public abstract class DraftCube {

    public class CardIdentity {

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

    private static final Logger logger = Logger.getLogger(DraftCube.class);

    private final String name;
    private final String code;
    private static final int boosterSize = 15;

    protected List<CardIdentity> cubeCards = new ArrayList<>();
    protected List<CardIdentity> leftCubeCards = new ArrayList<>();

    public DraftCube(String name) {
        this.name = name;
        this.code = getClass().getSimpleName();
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
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
                        booster.add(cardInfo.getCard());
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

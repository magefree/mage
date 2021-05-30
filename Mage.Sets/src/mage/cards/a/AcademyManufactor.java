package mage.cards.a;

import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.ClueArtifactToken;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author weirddan455
 */
public final class AcademyManufactor extends CardImpl {

    public AcademyManufactor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.ASSEMBLY_WORKER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // If you would create a Clue, Food, or Treasure token, instead create one of each.
        this.addAbility(new SimpleStaticAbility(new AcademyManufactorEffect()));
    }

    private AcademyManufactor(final AcademyManufactor card) {
        super(card);
    }

    @Override
    public AcademyManufactor copy() {
        return new AcademyManufactor(this);
    }
}

class AcademyManufactorEffect extends ReplacementEffectImpl {

    public AcademyManufactorEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "If you would create a Clue, Food, or Treasure token, instead create one of each";
    }

    private AcademyManufactorEffect(final AcademyManufactorEffect effect) {
        super(effect);
    }

    @Override
    public AcademyManufactorEffect copy() {
        return new AcademyManufactorEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event instanceof CreateTokenEvent && event.getPlayerId().equals(source.getControllerId())) {
            CreateTokenEvent tokenEvent = (CreateTokenEvent) event;
            for (Token token : tokenEvent.getTokens().keySet()) {
                if (token instanceof ClueArtifactToken || token instanceof FoodToken || token instanceof TreasureToken) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (event instanceof CreateTokenEvent) {
            CreateTokenEvent tokenEvent = (CreateTokenEvent) event;
            int clues = 0;
            int food = 0;
            int treasures = 0;
            ClueArtifactToken clueToken = null;
            FoodToken foodToken = null;
            TreasureToken treasureToken = null;
            Map<Token, Integer> tokens = tokenEvent.getTokens();

            for (Map.Entry<Token, Integer> entry : tokens.entrySet()) {
                Token token = entry.getKey();
                int amount = entry.getValue();
                if (token instanceof ClueArtifactToken) {
                    clueToken = (ClueArtifactToken) token;
                    clues += amount;
                }
                else if (token instanceof FoodToken) {
                    foodToken = (FoodToken) token;
                    food += amount;
                }
                else if (token instanceof TreasureToken) {
                    treasureToken = (TreasureToken) token;
                    treasures += amount;
                }
            }

            if (clueToken == null) {
                clueToken = new ClueArtifactToken();
            }
            if (foodToken == null) {
                foodToken = new FoodToken();
            }
            if (treasureToken == null) {
                treasureToken = new TreasureToken();
            }

            int cluesToAdd = food + treasures;
            int foodToAdd = clues + treasures;
            int treasuresToAdd = clues + food;

            tokens.put(clueToken, tokens.getOrDefault(clueToken, 0) + cluesToAdd);
            tokens.put(foodToken, tokens.getOrDefault(foodToken, 0) + foodToAdd);
            tokens.put(treasureToken, tokens.getOrDefault(treasureToken, 0) + treasuresToAdd);
        }
        return false;
    }
}

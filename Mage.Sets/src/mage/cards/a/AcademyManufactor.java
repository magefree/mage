package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.ClueArtifactToken;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TreasureToken;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
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
        if (!(event instanceof CreateTokenEvent) || !event.getPlayerId().equals(source.getControllerId())) {
            return false;
        }
        for (Token token : ((CreateTokenEvent) event).getTokens().keySet()) {
            if (token.hasSubtype(SubType.CLUE, game)
                    || token.hasSubtype(SubType.FOOD, game)
                    || token.hasSubtype(SubType.TREASURE, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int amount = 0;
        Map<Token, Integer> tokens = ((CreateTokenEvent) event).getTokens();
        for (Iterator<Map.Entry<Token, Integer>> iter = tokens.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry<Token, Integer> entry = iter.next();
            Token token = entry.getKey();
            if (token.hasSubtype(SubType.CLUE, game)
                    || token.hasSubtype(SubType.FOOD, game)
                    || token.hasSubtype(SubType.TREASURE, game)) {
                amount += entry.getValue();
                iter.remove();
            }
        }

        tokens.put(new ClueArtifactToken(), amount);
        tokens.put(new FoodToken(), amount);
        tokens.put(new TreasureToken(), amount);
        return false;
    }
}

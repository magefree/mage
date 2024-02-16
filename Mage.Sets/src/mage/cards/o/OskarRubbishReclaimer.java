package mage.cards.o;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class OskarRubbishReclaimer extends CardImpl {

    private static final ValueHint hint = new ValueHint("Number of different mana values in your graveyard", OskarRubbishReclaimerValue.instance);

    public OskarRubbishReclaimer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.addSubType(SubType.HUMAN, SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // This spell costs {1} less to cast for each different mana value among cards in your graveyard.
        Effect spellReductionEffect = new SpellCostReductionForEachSourceEffect(1, OskarRubbishReclaimerValue.instance);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, spellReductionEffect).addHint(hint));

        // Whenever you discard a nonland card, you may cast it from your graveyard.
        // Optinal part is handled by the effect
        this.addAbility(new DiscardCardControllerTriggeredAbility(new OskarRubbishReclaimerCastEffect(), false, StaticFilters.FILTER_CARD_A_NON_LAND));
    }

    private OskarRubbishReclaimer(final OskarRubbishReclaimer card) {
        super(card);
    }

    @Override
    public OskarRubbishReclaimer copy() {
        return new OskarRubbishReclaimer(this);
    }
}

class OskarRubbishReclaimerCastEffect extends OneShotEffect {
    OskarRubbishReclaimerCastEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may cast it from your graveyard.";
    }

    private OskarRubbishReclaimerCastEffect(final OskarRubbishReclaimerCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = (Card) getValue("discardedCard");
        if (controller == null || card == null || game.getState().getZone(card.getId()) != Zone.GRAVEYARD) {
            return false;
        }

        if (!controller.chooseUse(Outcome.Benefit, "Cast " + card.getName() + "?", source, game)) {
            return false;
        }
        CardUtil.castSingle(controller, source, game, card);

        return true;
    }

    @Override
    public OskarRubbishReclaimerCastEffect copy() {
        return new OskarRubbishReclaimerCastEffect(this);
    }
}

enum OskarRubbishReclaimerValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        return player == null ? 0 : player
                .getGraveyard()
                .getCards(game)
                .stream()
                .map(MageObject::getManaValue)
                .distinct()
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public OskarRubbishReclaimerValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "different mana value among cards in your graveyard";
    }

    @Override
    public String toString() {
        return "1";
    }
}
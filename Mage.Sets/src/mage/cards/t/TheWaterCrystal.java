package mage.cards.t;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheWaterCrystal extends CardImpl {

    private static final FilterCard filter = new FilterCard("blue spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public TheWaterCrystal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);

        // Blue spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // If an opponent would mill one or more cards, they mill that many cards plus four instead.
        this.addAbility(new SimpleStaticAbility(new TheWaterCrystalEffect()));

        // {4}{U}{U}, {T}: Each opponent mills cards equal to the number of cards in your hand.
        Ability ability = new SimpleActivatedAbility(
                new MillCardsEachPlayerEffect(CardsInControllerHandCount.ANY, TargetController.OPPONENT)
                        .setText("each opponent mills cards equal to the number of cards in your hand"),
                new ManaCostsImpl<>("{4}{U}{U}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TheWaterCrystal(final TheWaterCrystal card) {
        super(card);
    }

    @Override
    public TheWaterCrystal copy() {
        return new TheWaterCrystal(this);
    }
}

class TheWaterCrystalEffect extends ReplacementEffectImpl {

    TheWaterCrystalEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "if an opponent would mill one or more cards, they mill that many cards plus four instead";
    }

    private TheWaterCrystalEffect(final TheWaterCrystalEffect effect) {
        super(effect);
    }

    @Override
    public TheWaterCrystalEffect copy() {
        return new TheWaterCrystalEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MILL_CARDS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getOpponents(source.getControllerId()).contains(event.getPlayerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 4));
        return false;
    }
}

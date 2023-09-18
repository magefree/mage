package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Electrodominance extends CardImpl {

    public Electrodominance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}{R}");

        // Electrodominance deals X damage to any target. You may cast a card with converted mana cost X or less from your hand without paying its mana cost.
        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new ElectrodominanceEffect());
    }

    private Electrodominance(final Electrodominance card) {
        super(card);
    }

    @Override
    public Electrodominance copy() {
        return new Electrodominance(this);
    }
}

class ElectrodominanceEffect extends OneShotEffect {

    ElectrodominanceEffect() {
        super(Outcome.Benefit);
        staticText = "You may cast a spell with mana value X " +
                "or less from your hand without paying its mana cost";
    }

    private ElectrodominanceEffect(final ElectrodominanceEffect effect) {
        super(effect);
    }

    @Override
    public ElectrodominanceEffect copy() {
        return new ElectrodominanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        FilterCard filter = new FilterCard();
        filter.add(new ManaValuePredicate(
                ComparisonType.FEWER_THAN, source.getManaCostsToPay().getX() + 1
        ));
        return CardUtil.castSpellWithAttributesForFree(controller, source, game, controller.getHand(), filter);
    }
}

package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author TheElk801
 */
public final class ScentOfCinder extends CardImpl {

    public ScentOfCinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Reveal any number of red cards in your hand. Scent of Cinder deals X damage to any target, where X is the number of cards revealed this way.
        this.getSpellAbility().addEffect(new ScentOfCinderEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private ScentOfCinder(final ScentOfCinder card) {
        super(card);
    }

    @Override
    public ScentOfCinder copy() {
        return new ScentOfCinder(this);
    }
}

class ScentOfCinderEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("any number of red cards in your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public ScentOfCinderEffect() {
        super(Outcome.Damage);
        this.staticText = "reveal any number of red cards in your hand. "
                + "{this} deals X damage to any target, where X is the number of cards revealed this way";
    }

    private ScentOfCinderEffect(final ScentOfCinderEffect effect) {
        super(effect);
    }

    @Override
    public ScentOfCinderEffect copy() {
        return new ScentOfCinderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        RevealTargetFromHandCost cost = new RevealTargetFromHandCost(new TargetCardInHand(0, Integer.MAX_VALUE, filter));
        if (!cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        int xValue = cost.getNumberRevealedCards();
        return new DamageTargetEffect(xValue).apply(game, source);
    }
}

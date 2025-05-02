package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class ScentOfIvy extends CardImpl {

    public ScentOfIvy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Reveal any number of green cards in your hand. Target creature gets +X/+X until end of turn, where X is the number of cards revealed this way.
        this.getSpellAbility().addEffect(new ScentOfIvyEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ScentOfIvy(final ScentOfIvy card) {
        super(card);
    }

    @Override
    public ScentOfIvy copy() {
        return new ScentOfIvy(this);
    }
}

class ScentOfIvyEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("any number of green cards in your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public ScentOfIvyEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "reveal any number of green cards in your hand. "
                + "Target creature gets +X/+X until end of turn, where X is the number of cards revealed this way";
    }

    private ScentOfIvyEffect(final ScentOfIvyEffect effect) {
        super(effect);
    }

    @Override
    public ScentOfIvyEffect copy() {
        return new ScentOfIvyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        RevealTargetFromHandCost cost = new RevealTargetFromHandCost(new TargetCardInHand(0, Integer.MAX_VALUE, filter));
        if (!cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        int xValue = cost.getNumberRevealedCards();
        game.addEffect(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn), source);
        return true;
    }
}

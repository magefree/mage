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
public final class ScentOfNightshade extends CardImpl {

    public ScentOfNightshade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Reveal any number of black cards in your hand. Target creature gets -X/-X until end of turn, where X is the number of cards revealed this way.
        this.getSpellAbility().addEffect(new ScentOfNightshadeEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ScentOfNightshade(final ScentOfNightshade card) {
        super(card);
    }

    @Override
    public ScentOfNightshade copy() {
        return new ScentOfNightshade(this);
    }
}

class ScentOfNightshadeEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("any number of black cards in your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public ScentOfNightshadeEffect() {
        super(Outcome.Detriment);
        this.staticText = "reveal any number of black cards in your hand. "
                + "Target creature gets -X/-X until end of turn, "
                + "where X is the number of cards revealed this way";
    }

    private ScentOfNightshadeEffect(final ScentOfNightshadeEffect effect) {
        super(effect);
    }

    @Override
    public ScentOfNightshadeEffect copy() {
        return new ScentOfNightshadeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        RevealTargetFromHandCost cost = new RevealTargetFromHandCost(new TargetCardInHand(0, Integer.MAX_VALUE, filter));
        if (!cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        int xValue = -1 * cost.getNumberRevealedCards();
        game.addEffect(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn), source);
        return true;
    }
}


package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class InduceDespair extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a creature card from your hand");

    public InduceDespair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");

        // As an additional cost to cast Induce Despair, reveal a creature card from your hand.
        // Target creature gets -X/-X until end of turn, where X is the revealed card's converted mana cost.
        this.getSpellAbility().addEffect(new InduceDespairEffect());
        this.getSpellAbility().addCost(new RevealTargetFromHandCost(new TargetCardInHand(filter)));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private InduceDespair(final InduceDespair card) {
        super(card);
    }

    @Override
    public InduceDespair copy() {
        return new InduceDespair(this);
    }
}

class InduceDespairEffect extends OneShotEffect {

    public InduceDespairEffect() {
        super(Outcome.UnboostCreature);
        staticText = "Target creature gets -X/-X until end of turn, where X is the revealed card's mana value";
    }

    public InduceDespairEffect(InduceDespairEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        RevealTargetFromHandCost cost = (RevealTargetFromHandCost) source.getCosts().get(0);
        Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
        if (cost != null && creature != null) {
            int cmcBoost = -1 * cost.manaValues;
            ContinuousEffect effect = new BoostTargetEffect(cmcBoost, cmcBoost, Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(creature.getId(), creature.getZoneChangeCounter(game)));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }

    @Override
    public InduceDespairEffect copy() {
        return new InduceDespairEffect(this);
    }

}

package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/*
 *
 * @author Ketsuban
 */
public class Soulshriek extends CardImpl {

    protected static final FilterCard filterCard = new FilterCard("creature cards");

    static {
        filterCard.add(new CardTypePredicate(CardType.CREATURE));
    }

    public Soulshriek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Target creature you control gets +X/+0 until end of turn, where X is the number of creature cards in your graveyard. Sacrifice that creature at the beginning of the next end step.
        this.getSpellAbility().addEffect(new SoulshriekEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    @Override
    public Card copy() {
        return null;
    }
}

class SoulshriekEffect extends OneShotEffect {

    public SoulshriekEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Target creature you control gets +X/+0 until end of turn, where X is the number of creature cards in your graveyard. Sacrifice that creature at the beginning of the next end step";
    }

    public SoulshriekEffect(final SoulshriekEffect effect) {
        super(effect);
    }

    @Override
    public SoulshriekEffect copy() {
        return new SoulshriekEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            ContinuousEffect boost = new BoostTargetEffect(new CardsInControllerGraveyardCount(Soulshriek.filterCard), new StaticValue(0), Duration.EndOfTurn);
            boost.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(boost, source);
            Effect sacrifice = new SacrificeTargetEffect("Sacrifice that creature at the beginning of the next end step", source.getControllerId());
            sacrifice.setTargetPointer(new FixedTarget(permanent, game));
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrifice), source);
            return true;
        }
        return false;
    }
}

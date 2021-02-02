package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/*
 *
 * @author Ketsuban
 */
public class Soulshriek extends CardImpl {

    public Soulshriek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target creature you control gets +X/+0 until end of turn, where X is the number 
        // of creature cards in your graveyard. Sacrifice that creature at the beginning of the next end step.
        CardsInControllerGraveyardCount count = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD);
        Effect effect = new BoostTargetEffect(count, StaticValue.get(0), Duration.EndOfTurn, true);
        effect.setText("Target creature you control gets +X/+0 until end of turn, where X is the number of creature cards in your graveyard.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new SoulshriekEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private Soulshriek(final Soulshriek card) {
        super(card);
    }

    @Override
    public Soulshriek copy() {
        return new Soulshriek(this);
    }
}

class SoulshriekEffect extends OneShotEffect {

    public SoulshriekEffect() {
        super(Outcome.Detriment);
        this.staticText = "Sacrifice that creature at the beginning of the next end step";
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
            Effect sacrifice = new SacrificeTargetEffect("Sacrifice that creature "
                    + "at the beginning of the next end step", source.getControllerId());
            sacrifice.setTargetPointer(new FixedTarget(permanent, game));
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrifice), source);
            return true;
        }
        return false;
    }
}

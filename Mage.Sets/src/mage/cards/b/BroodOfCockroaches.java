
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;

import static mage.constants.Outcome.Benefit;

/**
 *
 * @author mpouedras
 */
public final class BroodOfCockroaches extends CardImpl {

    public BroodOfCockroaches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Brood of Cockroaches is put into your graveyard from the battlefield,
        // at the beginning of the next end step,
        // you lose 1 life
        // and return Brood of Cockroaches to your hand.
        this.addAbility(new DiesTriggeredAbility(new BroodOfCockroachesEffect()));
    }

    public BroodOfCockroaches(final BroodOfCockroaches card) {
        super(card);
    }

    @Override
    public BroodOfCockroaches copy() {
        return new BroodOfCockroaches(this);
    }
}

class BroodOfCockroachesEffect extends OneShotEffect {
    private static final String effectText = "at the beginning of the next end step, you lose 1 life and return Brood of Cockroaches to your hand.";

    BroodOfCockroachesEffect() {
        super(Benefit);
        staticText = effectText;
    }

    BroodOfCockroachesEffect(BroodOfCockroachesEffect broodOfCockroachesEffect) {
        super(broodOfCockroachesEffect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedLifeLost =
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new LoseLifeSourceControllerEffect(1));
        game.addDelayedTriggeredAbility(delayedLifeLost, source);

        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("return {this} to your hand.");
        effect.setTargetPointer(new FixedTarget(source.getSourceId(), source.getSourceObjectZoneChangeCounter()));
        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
        game.addDelayedTriggeredAbility(delayedAbility, source);

        return true;
    }

    @Override
    public Effect copy() {
        return new BroodOfCockroachesEffect(this);
    }
}
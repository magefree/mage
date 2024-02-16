package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PufferExtract extends CardImpl {

    public PufferExtract(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // {X}, {T}: Target creature you control gets +X/+X until end of turn. Destroy it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(new PufferExtractEffect(), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private PufferExtract(final PufferExtract card) {
        super(card);
    }

    @Override
    public PufferExtract copy() {
        return new PufferExtract(this);
    }
}

class PufferExtractEffect extends OneShotEffect {

    PufferExtractEffect() {
        super(Outcome.Benefit);
        staticText = "Target creature you control gets +X/+X until end of turn. " +
                "Destroy it at the beginning of the next end step.";
    }

    private PufferExtractEffect(final PufferExtractEffect effect) {
        super(effect);
    }

    @Override
    public PufferExtractEffect copy() {
        return new PufferExtractEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = source.getManaCostsToPay().getX();
        game.addEffect(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn), source);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new DestroyTargetEffect().setTargetPointer(new FixedTarget(source.getFirstTarget(), game))
        ), source);
        return true;
    }
}
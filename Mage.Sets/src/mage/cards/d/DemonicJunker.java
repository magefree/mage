package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class DemonicJunker extends CardImpl {

    public DemonicJunker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}{B}");
        
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // When this Vehicle enters, for each player, destroy up to one target creature that player controls. If a creature you controlled was destroyed this way, put two +1/+1 counters on this Vehicle.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DemonicJunkerEffect()
                .setTargetPointer(new EachTargetPointer()))
                .setTriggerPhrase("When this Vehicle enters, ");
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        ability.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, false));
        this.addAbility(ability);

        // Crew 2
        this.addAbility(new CrewAbility(2));

    }

    private DemonicJunker(final DemonicJunker card) {
        super(card);
    }

    @Override
    public DemonicJunker copy() {
        return new DemonicJunker(this);
    }
}

class DemonicJunkerEffect extends OneShotEffect {

    DemonicJunkerEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "for each player, destroy up to one target creature that player controls. If a creature you controlled was destroyed this way, put two +1/+1 counters on this Vehicle";
    }

    public DemonicJunkerEffect(final DemonicJunkerEffect effect) {
        super(effect);
    }

    @Override
    public DemonicJunkerEffect copy() {
        return new DemonicJunkerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean giveCounters = false;
        for (UUID permanentId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent == null) {
                continue;
            }
            if (permanent.destroy(source, game, false)) {
                giveCounters = permanent.getControllerId().equals(source.getControllerId());
            }
            if (giveCounters) {
                Permanent vehicle = game.getPermanent(source.getSourceId());
                if (vehicle != null) {
                    vehicle.addCounters(CounterType.P1P1.createInstance(2), source.getControllerId(), source, game);
                }
            }
        }
        return true;
    }
}
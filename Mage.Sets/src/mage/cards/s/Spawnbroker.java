
package mage.cards.s;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;

import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth & L_J
 */
public final class Spawnbroker extends CardImpl {

    private static final String rule = "you may exchange control of target creature you control and target creature with power less than or equal to that creature's power an opponent controls";

    public Spawnbroker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        
        // When Spawnbroker enters the battlefield, you may exchange control of target creature you control and target creature with power less than or equal to that creature's power an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExchangeControlTargetEffect(Duration.Custom, rule, false, true), true);
        ability.addTarget(new TargetControlledCreatureWithPowerGreaterOrLessThanOpponentPermanent());
        ability.addTarget(new SpawnbrokerSecondTarget());
        this.addAbility(ability);

    }

    private Spawnbroker(final Spawnbroker card) {
        super(card);
    }

    @Override
    public Spawnbroker copy() {
        return new Spawnbroker(this);
    }
}

class TargetControlledCreatureWithPowerGreaterOrLessThanOpponentPermanent extends TargetControlledPermanent {

    public TargetControlledCreatureWithPowerGreaterOrLessThanOpponentPermanent() {
        super();
        this.filter = this.filter.copy();
        filter.add(CardType.CREATURE.getPredicate());
        setTargetName("creature you control");
    }

    public TargetControlledCreatureWithPowerGreaterOrLessThanOpponentPermanent(final TargetControlledCreatureWithPowerGreaterOrLessThanOpponentPermanent target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject targetSource = game.getObject(source);
        if(targetSource != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
                if (!targets.containsKey(permanent.getId()) && permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                    possibleTargets.add(permanent.getId());
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public TargetControlledCreatureWithPowerGreaterOrLessThanOpponentPermanent copy() {
        return new TargetControlledCreatureWithPowerGreaterOrLessThanOpponentPermanent(this);
    }
}

class SpawnbrokerSecondTarget extends TargetPermanent {

    private Permanent firstTarget = null;

    public SpawnbrokerSecondTarget() {
        super();
        this.filter = this.filter.copy();
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(CardType.CREATURE.getPredicate());
        setTargetName("creature with power less than or equal to that creature's power an opponent controls");
    }

    public SpawnbrokerSecondTarget(final SpawnbrokerSecondTarget target) {
        super(target);
        this.firstTarget = target.firstTarget;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        if (super.canTarget(id, source, game)) {
            Permanent target1 = game.getPermanent(source.getFirstTarget());
            Permanent opponentPermanent = game.getPermanent(id);
            if (target1 != null && opponentPermanent != null) {
                return target1.getPower().getValue() >= opponentPermanent.getPower().getValue();
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        if (firstTarget != null) {
            MageObject targetSource = game.getObject(source);
            if(targetSource != null) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
                    if (!targets.containsKey(permanent.getId()) && permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                        if (firstTarget.getPower().getValue() >= permanent.getPower().getValue()) {
                            possibleTargets.add(permanent.getId());
                        }
                    }
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
        firstTarget = game.getPermanent(source.getFirstTarget());
        return super.chooseTarget(Outcome.GainControl, playerId, source, game);
    }

    @Override
    public SpawnbrokerSecondTarget copy() {
        return new SpawnbrokerSecondTarget(this);
    }
}


package mage.cards.t;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Targets;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class TriumphOfGerrard extends CardImpl {

    public TriumphOfGerrard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this);
        // I, II — Put a +1/+1 counter on target creature you control with the greatest power.
        sagaAbility.addChapterEffect(
                this,
                SagaChapter.CHAPTER_I,
                SagaChapter.CHAPTER_II,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                new TriumphOfGerrardTargetCreature()
        );
        // III — Target creature you control with the greatest power gains flying, first strike, and lifelink until end of turn.
        Effects effects = new Effects();
        effects.add(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn)
                .setText("Target creature you control with the greatest power gains flying"));
        effects.add(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn)
                .setText(", first strike"));
        effects.add(new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn)
                .setText(", and lifelink until end of turn"));
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                effects, new Targets(new TriumphOfGerrardTargetCreature())
        );
        this.addAbility(sagaAbility);

    }

    private TriumphOfGerrard(final TriumphOfGerrard card) {
        super(card);
    }

    @Override
    public TriumphOfGerrard copy() {
        return new TriumphOfGerrard(this);
    }
}

class TriumphOfGerrardTargetCreature extends TargetControlledCreaturePermanent {

    public TriumphOfGerrardTargetCreature() {
        super();
        setTargetName("creature you control with the greatest power");
    }

    public TriumphOfGerrardTargetCreature(final TriumphOfGerrardTargetCreature target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (super.canTarget(controllerId, id, source, game)) {
            int maxPower = 0;
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                if (permanent.getPower().getValue() > maxPower) {
                    maxPower = permanent.getPower().getValue();
                }
            }
            Permanent targetPermanent = game.getPermanent(id);
            if (targetPermanent != null) {
                return targetPermanent.getPower().getValue() == maxPower;
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        int maxPower = 0;
        List<Permanent> activePermanents = game.getBattlefield().getActivePermanents(filter, sourceControllerId, sourceId, game);
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject targetSource = game.getObject(sourceId);
        if(targetSource == null){
            return possibleTargets;
        }
        for (Permanent permanent : activePermanents) {
            if (permanent.getPower().getValue() > maxPower) {
                maxPower = permanent.getPower().getValue();
            }
        }
        for (Permanent permanent : activePermanents) {
            if (!targets.containsKey(permanent.getId()) && permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                if (permanent.getPower().getValue() == maxPower) {
                    possibleTargets.add(permanent.getId());
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        return !possibleTargets(sourceId, sourceControllerId, game).isEmpty();
    }

    @Override
    public TriumphOfGerrardTargetCreature copy() {
        return new TriumphOfGerrardTargetCreature(this);
    }
}

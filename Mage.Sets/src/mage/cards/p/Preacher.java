package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.common.SourceHasRemainedInSameZoneCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponentsChoicePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public final class Preacher extends CardImpl {

    public Preacher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // You may choose not to untap Preacher during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {tap}: Gain control of target creature of an opponent's choice that he or she controls for as long as Preacher remains tapped.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreacherEffect(), new TapSourceCost());
        ability.addTarget(new TargetOpponentsChoicePermanent(1, 1, new FilterControlledCreaturePermanent(), false, true));
        this.addAbility(ability);

    }

    public Preacher(final Preacher card) {
        super(card);
    }

    @Override
    public Preacher copy() {
        return new Preacher(this);
    }
}

class PreacherEffect extends OneShotEffect {

    public PreacherEffect() {
        super(Outcome.GainControl);
        this.staticText = "Gain control of target creature of an opponent's choice that he or she controls for as long as {this} remains tapped";
    }

    public PreacherEffect(final PreacherEffect effect) {
        super(effect);
    }

    @Override
    public PreacherEffect copy() {
        return new PreacherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null
                && sourcePermanent != null) {
            Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
            if (targetPermanent != null) {
                SourceTappedCondition sourceTappedCondition = SourceTappedCondition.instance;
                SourceHasRemainedInSameZoneCondition conditionSourceSameZone = new SourceHasRemainedInSameZoneCondition(sourcePermanent.getId());
                SourceHasRemainedInSameZoneCondition conditionTargetSameZone = new SourceHasRemainedInSameZoneCondition(targetPermanent.getId());
                ConditionalContinuousEffect effect = new ConditionalContinuousEffect(
                        new GainControlTargetEffect(Duration.Custom),
                        new CompoundCondition(sourceTappedCondition, new CompoundCondition(conditionSourceSameZone, conditionTargetSameZone)),
                        "Gain control of target creature of an opponent's choice that he or she controls for as long as {this} remains tapped");
                effect.setTargetPointer(new FixedTarget(targetPermanent.getId()));
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }
}

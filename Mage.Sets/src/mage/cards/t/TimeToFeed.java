package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * Time to Feed has two targets: a creature an opponent controls and a creature you control.
 * If only one of those creatures is a legal target when Time to Feed tries to resolve,
 * the creatures won't fight and neither will deal or be dealt damage. If the creature
 * you don't control is the illegal target, you won't gain life when it dies.
 * If neither creature is a legal target when Time to Feed tries to resolve, the spell will
 * be countered and none of its effects will happen.
 * If the first target creature dies that turn, you'll gain 3 life no matter what caused the creature to die or who controls the creature at that time.
 *
 * @author LevelX2
 */
public final class TimeToFeed extends CardImpl {

    public TimeToFeed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");

        // Choose target creature an opponent controls. When that creature dies this turn, you gain 3 life. 
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new WhenTargetDiesDelayedTriggeredAbility(new GainLifeEffect(3)),
                true,
                "Choose target creature an opponent controls. "
        ));
        // Target creature you control fights that creature.
        Effect effect = new FightTargetsEffect();
        effect.setText("Target creature you control fights that creature. " +
                "<i>(Each deals damage equal to its power to the other.)</i>");
        this.getSpellAbility().addEffect(effect);

        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private TimeToFeed(final TimeToFeed card) {
        super(card);
    }

    @Override
    public TimeToFeed copy() {
        return new TimeToFeed(this);
    }
}

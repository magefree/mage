package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DerelictAtticWidowsWalk extends RoomCard {

    public DerelictAtticWidowsWalk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, "{2}{B}", "{3}{B}");

        // Derelict Attic
        // When you unlock this door, you draw two cards and you lose 2 life.
        Ability ability = new UnlockThisDoorTriggeredAbility(
                new DrawCardSourceControllerEffect(2, true), false, true
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(2).concatBy("and"));
        this.getLeftHalfCard().addAbility(ability);

        // Widow's Walk
        // Whenever a creature you control attacks alone, it gets +1/+0 and gains deathtouch until end of turn.
        ability = new AttacksAloneControlledTriggeredAbility(
                new BoostTargetEffect(1, 0).setText("it gets +1/+0"),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, true, false
        );
        ability.addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance())
                .setText("and gains deathtouch until end of turn"));
        this.getRightHalfCard().addAbility(ability);
    }

    private DerelictAtticWidowsWalk(final DerelictAtticWidowsWalk card) {
        super(card);
    }

    @Override
    public DerelictAtticWidowsWalk copy() {
        return new DerelictAtticWidowsWalk(this);
    }
}

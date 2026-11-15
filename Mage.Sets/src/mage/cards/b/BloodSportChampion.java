package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author muz
 */
public final class BloodSportChampion extends CardImpl {

    public BloodSportChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {2}
        this.addAbility(new KickerAbility("{2}"));

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When this creature enters, if it was kicked, other creatures you control gain deathtouch until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainAbilityControlledEffect(
            DeathtouchAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES, true
        )).withInterveningIf(KickedCondition.ONCE));
    }

    private BloodSportChampion(final BloodSportChampion card) {
        super(card);
    }

    @Override
    public BloodSportChampion copy() {
        return new BloodSportChampion(this);
    }
}

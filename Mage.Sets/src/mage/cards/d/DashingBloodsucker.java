package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.EerieAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DashingBloodsucker extends CardImpl {

    public DashingBloodsucker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Eerie -- Whenever an enchantment you control enters and whenever you fully unlock a Room, Dashing Bloodsucker gets +2/+0 and gains lifelink until end of turn.
        Ability ability = new EerieAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn).setText("{this} gets +2/+0"));
        ability.addEffect(new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn).setText("and gains lifelink until end of turn"));
        this.addAbility(ability);
    }

    private DashingBloodsucker(final DashingBloodsucker card) {
        super(card);
    }

    @Override
    public DashingBloodsucker copy() {
        return new DashingBloodsucker(this);
    }
}

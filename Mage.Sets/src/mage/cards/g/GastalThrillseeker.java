package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GastalThrillseeker extends CardImpl {

    public GastalThrillseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // When this creature enters, it deals 1 damage to target opponent and you gain 1 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(1, "it"));
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Max speed -- This creature has deathtouch and haste.
        ability = new SimpleStaticAbility(new GainAbilitySourceEffect(DeathtouchAbility.getInstance()));
        ability.addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance()).setText("and haste"));
        this.addAbility(new MaxSpeedAbility(ability));
    }

    private GastalThrillseeker(final GastalThrillseeker card) {
        super(card);
    }

    @Override
    public GastalThrillseeker copy() {
        return new GastalThrillseeker(this);
    }
}

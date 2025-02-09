package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwiftwingAssailant extends CardImpl {

    public SwiftwingAssailant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // Max speed -- This creature gets +0/+1 and has vigilance.
        Ability ability = new SimpleStaticAbility(new BoostSourceEffect(0, 1, Duration.WhileOnBattlefield));
        ability.addEffect(new GainAbilitySourceEffect(VigilanceAbility.getInstance()).setText("and has vigilance"));
        this.addAbility(new MaxSpeedAbility(ability));
    }

    private SwiftwingAssailant(final SwiftwingAssailant card) {
        super(card);
    }

    @Override
    public SwiftwingAssailant copy() {
        return new SwiftwingAssailant(this);
    }
}

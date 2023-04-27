package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.keyword.AdaptEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LivingMetalAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JetfireAirGuardian extends CardImpl {

    public JetfireAirGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.color.setBlue(true);
        this.nightCard = true;

        // Living metal
        this.addAbility(new LivingMetalAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {U}{U}{U}: Convert Jetfire, then adapt 3.
        Ability ability = new SimpleActivatedAbility(
                new TransformSourceEffect()
                        .setText("convert {this}"),
                new ManaCostsImpl<>("{U}{U}{U}")
        );
        ability.addEffect(new AdaptEffect(3).concatBy(", then"));
        this.addAbility(ability);
    }

    private JetfireAirGuardian(final JetfireAirGuardian card) {
        super(card);
    }

    @Override
    public JetfireAirGuardian copy() {
        return new JetfireAirGuardian(this);
    }
}

package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PlainscyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SoaringSandwing extends CardImpl {

    public SoaringSandwing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Soaring Sandwing enters the battlefield, you gain 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)));

        // Plainscycling {2}
        this.addAbility(new PlainscyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private SoaringSandwing(final SoaringSandwing card) {
        super(card);
    }

    @Override
    public SoaringSandwing copy() {
        return new SoaringSandwing(this);
    }
}

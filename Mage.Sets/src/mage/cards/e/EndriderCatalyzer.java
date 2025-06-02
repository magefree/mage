package mage.cards.e;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EndriderCatalyzer extends CardImpl {

    public EndriderCatalyzer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // Max speed -- {T}: Add {R}{R}.
        this.addAbility(new MaxSpeedAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD, Mana.RedMana(2), new TapSourceCost()
        )));
    }

    private EndriderCatalyzer(final EndriderCatalyzer card) {
        super(card);
    }

    @Override
    public EndriderCatalyzer copy() {
        return new EndriderCatalyzer(this);
    }
}

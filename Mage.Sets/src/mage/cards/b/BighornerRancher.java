package mage.cards.b;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.GreatestPowerAmongControlledCreaturesValue;
import mage.abilities.dynamicvalue.common.GreatestToughnessAmongControlledCreaturesValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class BighornerRancher extends CardImpl {

    public BighornerRancher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {T}: Add an amount of {G} equal to the greatest power among creatures you control.
        this.addAbility(new DynamicManaAbility(
                Mana.GreenMana(1), GreatestPowerAmongControlledCreaturesValue.instance, new TapSourceCost(),
                "Add an amount of {G} equal to the greatest power among creatures you control."
        ));

        // Sacrifice Bighorner Rancher: You gain life equal to the greatest toughness among other creatures you control.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainLifeEffect(GreatestToughnessAmongControlledCreaturesValue.instance).setText("You gain life equal to the greatest toughness among other creatures you control."),
                new SacrificeSourceCost()));
    }

    private BighornerRancher(final BighornerRancher card) {
        super(card);
    }

    @Override
    public BighornerRancher copy() {
        return new BighornerRancher(this);
    }
}

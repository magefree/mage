
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostPairedEffect;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author noxx

 */
public final class DruidsFamiliar extends CardImpl {

    private static final String ruleText = "As long as {this} is paired with another creature, each of those creatures gets +2/+2";

    public DruidsFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.BEAR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Druid's Familiar is paired with another creature, each of those creatures gets +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostPairedEffect(2, 2, ruleText)));
    }

    private DruidsFamiliar(final DruidsFamiliar card) {
        super(card);
    }

    @Override
    public DruidsFamiliar copy() {
        return new DruidsFamiliar(this);
    }
}

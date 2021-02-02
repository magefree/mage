
package mage.cards.w;

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
 * @author noxx
 */
public final class WolfirSilverheart extends CardImpl {

    private static final String ruleText = "As long as {this} is paired with another creature, each of those creatures gets +4/+4";

    public WolfirSilverheart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.WOLF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Wolfir Silverheart is paired with another creature, each of those creatures gets +4/+4.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostPairedEffect(4, 4, ruleText)));
    }

    private WolfirSilverheart(final WolfirSilverheart card) {
        super(card);
    }

    @Override
    public WolfirSilverheart copy() {
        return new WolfirSilverheart(this);
    }
}

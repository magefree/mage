
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityPairedEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author noxx
 */
public final class GeistTrappers extends CardImpl {

    private static final String ruleText = "As long as {this} is paired with another creature, both creatures have reach";

    public GeistTrappers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Geist Trappers is paired with another creature, both creatures have reach.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityPairedEffect(ReachAbility.getInstance(), ruleText)));
    }

    private GeistTrappers(final GeistTrappers card) {
        super(card);
    }

    @Override
    public GeistTrappers copy() {
        return new GeistTrappers(this);
    }
}

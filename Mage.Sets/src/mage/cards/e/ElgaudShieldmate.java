
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityPairedEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author noxx
 */
public final class ElgaudShieldmate extends CardImpl {

    private static final String ruleText = "As long as {this} is paired with another creature, both creatures have hexproof";

    public ElgaudShieldmate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Elgaud Shieldmate is paired with another creature, both creatures have hexproof.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityPairedEffect(HexproofAbility.getInstance(), ruleText)));
    }

    private ElgaudShieldmate(final ElgaudShieldmate card) {
        super(card);
    }

    @Override
    public ElgaudShieldmate copy() {
        return new ElgaudShieldmate(this);
    }
}

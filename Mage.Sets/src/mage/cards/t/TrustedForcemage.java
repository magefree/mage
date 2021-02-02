
package mage.cards.t;

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
public final class TrustedForcemage extends CardImpl {

    private static final String ruleText = "As long as {this} is paired with another creature, each of those creatures gets +1/+1";

    public TrustedForcemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Trusted Forcemage is paired with another creature, each of those creatures gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostPairedEffect(1, 1, ruleText)));
    }

    private TrustedForcemage(final TrustedForcemage card) {
        super(card);
    }

    @Override
    public TrustedForcemage copy() {
        return new TrustedForcemage(this);
    }
}
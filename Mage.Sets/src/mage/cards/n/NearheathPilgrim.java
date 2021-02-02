
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityPairedEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author noxx
 */
public final class NearheathPilgrim extends CardImpl {

    private static final String ruleText = "As long as {this} is paired with another creature, both creatures have lifelink";

    public NearheathPilgrim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Nearheath Pilgrim is paired with another creature, both creatures have lifelink.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityPairedEffect(LifelinkAbility.getInstance(), ruleText)));
    }

    private NearheathPilgrim(final NearheathPilgrim card) {
        super(card);
    }

    @Override
    public NearheathPilgrim copy() {
        return new NearheathPilgrim(this);
    }
}


package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BloodthirstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class BloodscaleProwler extends CardImpl {

    public BloodscaleProwler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.VIASHINO, SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Bloodthirst 1
        this.addAbility(new BloodthirstAbility(1));
    }

    private BloodscaleProwler(final BloodscaleProwler card) {
        super(card);
    }

    @Override
    public BloodscaleProwler copy() {
        return new BloodscaleProwler(this);
    }
}


package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.MustBeBlockedByAllSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class ElvishBard extends CardImpl {

    public ElvishBard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.subtype.add(SubType.BARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // All creatures able to block Elvish Bard do so.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MustBeBlockedByAllSourceEffect()));
    }

    private ElvishBard(final ElvishBard card) {
        super(card);
    }

    @Override
    public ElvishBard copy() {
        return new ElvishBard(this);
    }
}

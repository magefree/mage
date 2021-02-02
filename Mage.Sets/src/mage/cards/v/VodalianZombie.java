
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Jgod
 */
public final class VodalianZombie extends CardImpl {

    public VodalianZombie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{B}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Protection from green
        this.addAbility(ProtectionAbility.from(ObjectColor.GREEN));
    }

    private VodalianZombie(final VodalianZombie card) {
        super(card);
    }

    @Override
    public VodalianZombie copy() {
        return new VodalianZombie(this);
    }
}

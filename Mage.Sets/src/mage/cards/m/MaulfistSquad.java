
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FabricateAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class MaulfistSquad extends CardImpl {

    public MaulfistSquad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility(false));
        
        // Fabricate 1
        this.addAbility(new FabricateAbility(1));
    }

    private MaulfistSquad(final MaulfistSquad card) {
        super(card);
    }

    @Override
    public MaulfistSquad copy() {
        return new MaulfistSquad(this);
    }
}

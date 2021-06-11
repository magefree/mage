
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class VaultSkirge extends CardImpl {

    public VaultSkirge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}{B/P}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.IMP);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(LifelinkAbility.getInstance());
    }

    private VaultSkirge(final VaultSkirge card) {
        super(card);
    }

    @Override
    public VaultSkirge copy() {
        return new VaultSkirge(this);
    }
}

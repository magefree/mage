

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SacredWolf extends CardImpl {
    public SacredWolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.WOLF);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
        this.addAbility(HexproofAbility.getInstance());
    }

    private SacredWolf(final SacredWolf card) {
        super(card);
    }

    @Override
    public SacredWolf copy() {
        return new SacredWolf(this);
    }

}


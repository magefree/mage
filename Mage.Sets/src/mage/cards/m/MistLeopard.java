

package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class MistLeopard extends CardImpl {

    public MistLeopard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
    this.toughness = new MageInt(2);
        this.addAbility(ShroudAbility.getInstance());
    }

    private MistLeopard(final MistLeopard card) {
        super(card);
    }

    @Override
    public MistLeopard copy() {
        return new MistLeopard(this);
    }

}

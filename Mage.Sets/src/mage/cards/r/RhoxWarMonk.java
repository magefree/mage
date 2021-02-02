

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class RhoxWarMonk extends CardImpl {

    public RhoxWarMonk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{W}{U}");
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.MONK);


        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(LifelinkAbility.getInstance());
    }

    private RhoxWarMonk(final RhoxWarMonk card) {
        super(card);
    }

    @Override
    public RhoxWarMonk copy() {
        return new RhoxWarMonk(this);
    }

}

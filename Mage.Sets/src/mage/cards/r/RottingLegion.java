

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class RottingLegion extends CardImpl {

    public RottingLegion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private RottingLegion(final RottingLegion card) {
        super(card);
    }

    @Override
    public RottingLegion copy() {
        return new RottingLegion(this);
    }

}

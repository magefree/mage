
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FabricateAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class IronLeagueSteed extends CardImpl {

    public IronLeagueSteed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Fabricate 1
        this.addAbility(new FabricateAbility(1));
    }

    private IronLeagueSteed(final IronLeagueSteed card) {
        super(card);
    }

    @Override
    public IronLeagueSteed copy() {
        return new IronLeagueSteed(this);
    }
}

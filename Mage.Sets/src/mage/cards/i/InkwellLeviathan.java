

package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.IslandwalkAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class InkwellLeviathan extends CardImpl {

    public InkwellLeviathan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{7}{U}{U}");
        this.subtype.add(SubType.LEVIATHAN);

        this.power = new MageInt(7);
        this.toughness = new MageInt(11);

        this.addAbility(new IslandwalkAbility());
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(ShroudAbility.getInstance());
    }

    private InkwellLeviathan(final InkwellLeviathan card) {
        super(card);
    }

    @Override
    public InkwellLeviathan copy() {
        return new InkwellLeviathan(this);
    }

}

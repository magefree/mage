
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class OpalineUnicorn extends CardImpl {

    public OpalineUnicorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.UNICORN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility(new TapSourceCost()));
    }

    private OpalineUnicorn(final OpalineUnicorn card) {
        super(card);
    }

    @Override
    public OpalineUnicorn copy() {
        return new OpalineUnicorn(this);
    }
}


package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SatyrNyxSmithElementalToken;

/**
 *
 * @author LevelX2
 */
public final class SatyrNyxSmith extends CardImpl {

    public SatyrNyxSmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.SATYR);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // <i>Inspired</i> &mdash; Whenever Satyr Nyx-Smith becomes untapped, you may pay {2}{R}. If you do, create a 3/1 red Elemental enchantment creature token with haste.
        this.addAbility(new InspiredAbility(new DoIfCostPaid(new CreateTokenEffect(new SatyrNyxSmithElementalToken()), new ManaCostsImpl<>("{2}{R}"))));

    }

    private SatyrNyxSmith(final SatyrNyxSmith card) {
        super(card);
    }

    @Override
    public SatyrNyxSmith copy() {
        return new SatyrNyxSmith(this);
    }
}

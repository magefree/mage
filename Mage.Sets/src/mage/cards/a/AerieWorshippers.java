
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.AerieWorshippersBirdToken;

/**
 *
 * @author LevelX2
 */
public final class AerieWorshippers extends CardImpl {

    public AerieWorshippers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // <i>Inspired</i> &mdash; Whenever Aerie Worshipers becomes untapped, you may pay {2}{U}. If you do, create a 2/2 blue Bird enchantment creature token with flying.
        this.addAbility(new InspiredAbility(new DoIfCostPaid(new CreateTokenEffect(new AerieWorshippersBirdToken()), new ManaCostsImpl<>("{2}{U}"))));
    }

    private AerieWorshippers(final AerieWorshippers card) {
        super(card);
    }

    @Override
    public AerieWorshippers copy() {
        return new AerieWorshippers(this);
    }
}

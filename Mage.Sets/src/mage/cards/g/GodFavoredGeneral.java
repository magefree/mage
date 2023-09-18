
package mage.cards.g;

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
import mage.game.permanent.token.GodFavoredGeneralSoldierToken;

/**
 *
 * @author LevelX2
 */
public final class GodFavoredGeneral extends CardImpl {

    public GodFavoredGeneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // <i>Inspired</i> &mdash; Whenever God-Favored General becomes untapped, you may pay {2}{W}. If you do, create two 1/1 white Soldier enchantment creature tokens.
        this.addAbility(new InspiredAbility(new DoIfCostPaid(new CreateTokenEffect(new GodFavoredGeneralSoldierToken(), 2), new ManaCostsImpl<>("{2}{W}"))));
    }

    private GodFavoredGeneral(final GodFavoredGeneral card) {
        super(card);
    }

    @Override
    public GodFavoredGeneral copy() {
        return new GodFavoredGeneral(this);
    }
}

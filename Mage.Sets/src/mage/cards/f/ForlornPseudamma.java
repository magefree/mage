
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.InspiredAbility;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ForlornPseudammaZombieToken;

/**
 *
 * @author LevelX2
 */
public final class ForlornPseudamma extends CardImpl {

    public ForlornPseudamma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Intimidate
        this.addAbility(IntimidateAbility.getInstance());
        // <i>Inspired</i> &mdash; Whenever Forlorn Pseudamma becomes untapped, you may pay {2}{B}. If you do, create a 2/2 black Zombie enchantment creature token.
        this.addAbility(new InspiredAbility(new DoIfCostPaid(new CreateTokenEffect(new ForlornPseudammaZombieToken()), new ManaCostsImpl<>("{2}{B}"))));
    }

    private ForlornPseudamma(final ForlornPseudamma card) {
        super(card);
    }

    @Override
    public ForlornPseudamma copy() {
        return new ForlornPseudamma(this);
    }
}

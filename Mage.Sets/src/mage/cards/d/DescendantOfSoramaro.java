
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX
 */
public final class DescendantOfSoramaro extends CardImpl {

    public DescendantOfSoramaro(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        // {1}{U}: Look at the top X cards of your library, where X is the number of cards in your hand, then put them back in any order.
        Effect effect = new LookLibraryControllerEffect(CardsInControllerHandCount.instance);
        effect.setText("Look at the top X cards of your library, where X is the number of cards in your hand, then put them back in any order");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                effect, new ManaCostsImpl<>("{1}{U}")));
    }

    private DescendantOfSoramaro(final DescendantOfSoramaro card) {
        super(card);
    }

    @Override
    public DescendantOfSoramaro copy() {
        return new DescendantOfSoramaro(this);
    }
}


package mage.cards.p;

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
import mage.game.permanent.token.CentaurEnchantmentCreatureToken;

/**
 *
 * @author LevelX2
 */
public final class PheresBandRaiders extends CardImpl {

    public PheresBandRaiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // <i>Inspired</i> &mdash; Whenever Pheres-Band Raiders becomes untapped, you may pay {2}{G}. If you do, create a 3/3 green Centaur enchantment creature token.
        this.addAbility(new InspiredAbility(new DoIfCostPaid(new CreateTokenEffect(new CentaurEnchantmentCreatureToken()), new ManaCostsImpl<>("{2}{G}"))));

    }

    private PheresBandRaiders(final PheresBandRaiders card) {
        super(card);
    }

    @Override
    public PheresBandRaiders copy() {
        return new PheresBandRaiders(this);
    }
}



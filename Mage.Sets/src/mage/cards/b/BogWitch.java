
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class BogWitch extends CardImpl {

    public BogWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN, SubType.SPELLSHAPER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {B}, {tap}, Discard a card: Add {B}{B}{B}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(3), new ManaCostsImpl<>("{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private BogWitch(final BogWitch card) {
        super(card);
    }

    @Override
    public BogWitch copy() {
        return new BogWitch(this);
    }
}

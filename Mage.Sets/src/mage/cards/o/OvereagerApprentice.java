
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author cbt33, Loki (Dark Ritual)
 */
public final class OvereagerApprentice extends CardImpl {

    public OvereagerApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MINION);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Discard a card, Sacrifice Overeager Apprentice: Add {B}{B}{B}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(3), new DiscardCardCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private OvereagerApprentice(final OvereagerApprentice card) {
        super(card);
    }

    @Override
    public OvereagerApprentice copy() {
        return new OvereagerApprentice(this);
    }
}

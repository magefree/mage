
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class MesmericTrance extends CardImpl {

    public MesmericTrance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}{U}");

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));
        // {U}, Discard a card: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{U}"));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private MesmericTrance(final MesmericTrance card) {
        super(card);
    }

    @Override
    public MesmericTrance copy() {
        return new MesmericTrance(this);
    }
}

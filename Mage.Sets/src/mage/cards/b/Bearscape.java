
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BearToken;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class Bearscape extends CardImpl {

    public Bearscape(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}{G}");


        // {1}{G}, Exile two cards from your graveyard: Create a 2/2 green Bear creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new BearToken()), new ManaCostsImpl("{1}{G}"));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(2, StaticFilters.FILTER_CARDS_FROM_YOUR_GRAVEYARD)));
        this.addAbility(ability);
    }

    private Bearscape(final Bearscape card) {
        super(card);
    }

    @Override
    public Bearscape copy() {
        return new Bearscape(this);
    }
}

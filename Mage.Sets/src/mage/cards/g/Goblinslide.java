
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.GoblinToken;

/**
 *
 * @author emerald000
 */
public final class Goblinslide extends CardImpl {

    public Goblinslide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Whenever you cast a noncreature spell, you may pay {1}. If you do, create a 1/1 red Goblin creature token with haste.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DoIfCostPaid(
                new CreateTokenEffect(new GoblinToken(true)),
                new GenericManaCost(1)),
                StaticFilters.FILTER_SPELL_NON_CREATURE,
                false
        ));
    }

    private Goblinslide(final Goblinslide card) {
        super(card);
    }

    @Override
    public Goblinslide copy() {
        return new Goblinslide(this);
    }
}

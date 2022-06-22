
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward
 */
public final class RooftopStorm extends CardImpl {

    public RooftopStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{U}");

        // You may pay {0} rather than pay the mana cost for Zombie creature spells you cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RooftopStormRuleEffect()));

    }

    private RooftopStorm(final RooftopStorm card) {
        super(card);
    }

    @Override
    public RooftopStorm copy() {
        return new RooftopStorm(this);
    }
}

class RooftopStormRuleEffect extends ContinuousEffectImpl {

    private static final FilterCard filter = new FilterCard("Zombie creature spells");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
        filter.add(CardType.CREATURE.getPredicate());
    }

    private final AlternativeCostSourceAbility alternativeCastingCostAbility
            = new AlternativeCostSourceAbility(new ManaCostsImpl<>("{0}"), SourceIsSpellCondition.instance, null, filter, true);

    public RooftopStormRuleEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "You may pay {0} rather than pay the mana cost for Zombie creature spells you cast";
    }

    public RooftopStormRuleEffect(final RooftopStormRuleEffect effect) {
        super(effect);
    }

    @Override
    public RooftopStormRuleEffect copy() {
        return new RooftopStormRuleEffect(this);
    }

    @Override
    public void init(Ability source, Game game, UUID activePlayerId) {
        super.init(source, game, activePlayerId);
        alternativeCastingCostAbility.setSourceId(source.getSourceId());
    }
    
    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.getAlternativeSourceCosts().add(alternativeCastingCostAbility);
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}

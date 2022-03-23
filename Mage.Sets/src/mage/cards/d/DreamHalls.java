
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.SharesColorWithSourcePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class DreamHalls extends CardImpl {

    public DreamHalls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}{U}");

        // Rather than pay the mana cost for a spell, its controller may discard a card that shares a color with that spell.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DreamHallsEffect()));
    }

    private DreamHalls(final DreamHalls card) {
        super(card);
    }

    @Override
    public DreamHalls copy() {
        return new DreamHalls(this);
    }
}

class DreamHallsEffect extends ContinuousEffectImpl {

    private static final FilterCard filter = new FilterCard("a card that shares a color with that spell");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SharesColorWithSourcePredicate.instance);
    }

    private final AlternativeCostSourceAbility alternativeCastingCostAbility = new AlternativeCostSourceAbility(new DiscardCardCost(filter), SourceIsSpellCondition.instance);

    public DreamHallsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Rather than pay the mana cost for a spell, its controller may discard a card that shares a color with that spell";
    }

    public DreamHallsEffect(final DreamHallsEffect effect) {
        super(effect);
    }

    @Override
    public DreamHallsEffect copy() {
        return new DreamHallsEffect(this);
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
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.getAlternativeSourceCosts().add(alternativeCastingCostAbility);
                }
            }

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

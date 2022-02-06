
package mage.cards.w;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class WardOfBones extends CardImpl {

    public WardOfBones(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // Each opponent who controls more creatures than you can't play creature cards. The same is true for artifacts, enchantments, and lands.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WardOfBonesEffect()));

    }

    private WardOfBones(final WardOfBones card) {
        super(card);
    }

    @Override
    public WardOfBones copy() {
        return new WardOfBones(this);
    }
}

class WardOfBonesEffect extends ContinuousRuleModifyingEffectImpl {

    public WardOfBonesEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Each opponent who controls more creatures than you can't cast creature spells. "
                + "The same is true for artifacts and enchantments.<br><br>"
                + "Each opponent who controls more lands than you can't play lands.";
    }

    public WardOfBonesEffect(final WardOfBonesEffect effect) {
        super(effect);
    }

    @Override
    public WardOfBonesEffect copy() {
        return new WardOfBonesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You can't play the land or cast the spell (" + mageObject.getLogName() + " in play).";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLAY_LAND || event.getType() == GameEvent.EventType.CAST_SPELL;

    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            final Card card = game.getCard(event.getSourceId());
            final Player opponent = game.getPlayer(event.getPlayerId());
            if (card == null || opponent == null) {
                return false;
            }
            if (card.isCreature(game)
                    && game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, opponent.getId(), game)
                    > game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)) {
                return true;
            }
            if (card.isArtifact(game)
                    && game.getBattlefield().countAll(new FilterArtifactPermanent(), opponent.getId(), game)
                    > game.getBattlefield().countAll(new FilterArtifactPermanent(), source.getControllerId(), game)) {
                return true;
            }
            if (card.isEnchantment(game)
                    && game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_ENCHANTMENT, opponent.getId(), game)
                    > game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_ENCHANTMENT, source.getControllerId(), game)) {
                return true;
            }
            final int yourLands = game.getBattlefield().countAll(new FilterLandPermanent(), source.getControllerId(), game);
            if (card.isLand(game)
                    && game.getBattlefield().countAll(new FilterLandPermanent(), opponent.getId(), game) > yourLands) {
                return true;
            }
        }
        return false;
    }
}

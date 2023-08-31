
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.ManaPool;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class Upwelling extends CardImpl {

    public Upwelling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // Mana pools don't empty as steps and phases end.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UpwellingRuleEffect()));

    }

    private Upwelling(final Upwelling card) {
        super(card);
    }

    @Override
    public Upwelling copy() {
        return new Upwelling(this);
    }
}

class UpwellingRuleEffect extends ContinuousEffectImpl {

    public UpwellingRuleEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Players don't lose unspent mana as steps and phases end";
    }

    private UpwellingRuleEffect(final UpwellingRuleEffect effect) {
        super(effect);
    }

    @Override
    public UpwellingRuleEffect copy() {
        return new UpwellingRuleEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    ManaPool pool = player.getManaPool();
                    pool.addDoNotEmptyManaType(ManaType.WHITE);
                    pool.addDoNotEmptyManaType(ManaType.GREEN);
                    pool.addDoNotEmptyManaType(ManaType.BLUE);
                    pool.addDoNotEmptyManaType(ManaType.RED);
                    pool.addDoNotEmptyManaType(ManaType.BLACK);
                    pool.addDoNotEmptyManaType(ManaType.COLORLESS);
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


package mage.cards.b;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */
public final class BloodSun extends CardImpl {

    public BloodSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // When Blood Sun enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false));

        // All lands lose all abilities except mana abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BloodSunEffect(Duration.WhileOnBattlefield)));
    }

    private BloodSun(final BloodSun card) {
        super(card);
    }

    @Override
    public BloodSun copy() {
        return new BloodSun(this);
    }
}

class BloodSunEffect extends ContinuousEffectImpl {

    public BloodSunEffect(Duration duration) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.LoseAbility);
        staticText = "All lands lose all abilities except mana abilities";
    }

    private BloodSunEffect(final BloodSunEffect effect) {
        super(effect);
    }

    @Override
    public BloodSunEffect copy() {
        return new BloodSunEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ObjectColor colorless = new ObjectColor();
            // permaments
            game.getBattlefield().getActivePermanents(StaticFilters.FILTER_LANDS, source.getControllerId(), game)
                    .forEach(permanent -> {
                        if (layer == Layer.AbilityAddingRemovingEffects_6) {
                            List<Ability> toRemove = new ArrayList<>();
                            permanent.getAbilities().forEach(a -> {
                                if (a.getAbilityType() != AbilityType.MANA) {
                                    toRemove.add(a);
                                }
                            });
                            permanent.removeAbilities(toRemove, source.getSourceId(), game);
                        }
                    });
            // exile
            game.getExile().getAllCards(game).forEach(c -> removeActivatedAndStaticAbilitiesFromLands(c));

            game.getState().getPlayersInRange(controller.getId(), game).forEach(playerId -> {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    // hand
                    player.getHand().getCards(game).forEach(c -> removeActivatedAndStaticAbilitiesFromLands(c));
                    // library
                    player.getLibrary().getCards(game).forEach(c -> removeActivatedAndStaticAbilitiesFromLands(c));
                    // graveyard
                    player.getGraveyard().getCards(game).forEach(c -> removeActivatedAndStaticAbilitiesFromLands(c));
                }
            });
            return true;
        }
        return false;
    }

    private void removeActivatedAndStaticAbilitiesFromLands(Card card) {
        if (card.isLand() && (layer == Layer.AbilityAddingRemovingEffects_6)) {
            List<Ability> toRemove = new ArrayList<>();
            card.getAbilities().forEach(a -> {
                if (a.getAbilityType() != AbilityType.MANA && a.getAbilityType() != AbilityType.PLAY_LAND) {
                    toRemove.add(a);
                }
            });
            toRemove.forEach(a -> {
                if (card.getAbilities().contains(a))
                    card.getAbilities().remove(card.getAbilities().indexOf(a));
            });
        }
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6;
    }

}

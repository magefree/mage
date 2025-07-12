
package mage.cards.b;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BloodSun extends CardImpl {

    public BloodSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // When Blood Sun enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false));

        // All lands lose all abilities except mana abilities.
        this.addAbility(new SimpleStaticAbility(new BloodSunEffect(Duration.WhileOnBattlefield)));
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

    BloodSunEffect(Duration duration) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.LoseAbility);
        staticText = "all lands lose all abilities except mana abilities";
    }

    private BloodSunEffect(final BloodSunEffect effect) {
        super(effect);
    }

    @Override
    public BloodSunEffect copy() {
        return new BloodSunEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            List<Ability> toRemove = new ArrayList<>();
            permanent.getAbilities().forEach(ability -> {
                if (!ability.getAbilityType().isManaAbility()) {
                    toRemove.add(ability);
                }
            });
            permanent.removeAbilities(toRemove, source.getSourceId(), game);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        affectedObjects.addAll(game.getBattlefield().getActivePermanents(StaticFilters.FILTER_LANDS, player.getId(), source, game));
        return !affectedObjects.isEmpty();
    }
}

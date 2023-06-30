package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class ShelteringPrayers extends CardImpl {

    public ShelteringPrayers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // Basic lands each player controls have shroud as long as that player controls three or fewer lands.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ShelteringPrayersEffect()));

    }

    private ShelteringPrayers(final ShelteringPrayers card) {
        super(card);
    }

    @Override
    public ShelteringPrayers copy() {
        return new ShelteringPrayers(this);
    }
}

class ShelteringPrayersEffect extends ContinuousEffectImpl {

    public ShelteringPrayersEffect() {
        super(Duration.WhileOnBattlefield, Outcome.AddAbility);
        staticText = "Basic lands each player controls have shroud as long as that player controls three or fewer lands.";
        dependencyTypes.add(DependencyType.AddingAbility);

    }

    public ShelteringPrayersEffect(final ShelteringPrayersEffect effect) {
        super(effect);
    }

    @Override
    public ShelteringPrayersEffect copy() {
        return new ShelteringPrayersEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null
                    && game.getBattlefield().getAllActivePermanents(new FilterLandPermanent(), playerId, game).size() < 4) {
                for (Permanent land : game.getBattlefield().getAllActivePermanents(new FilterLandPermanent(), playerId, game)) {
                    if (land != null
                            && land.isBasic(game)) {
                        switch (layer) {
                            case AbilityAddingRemovingEffects_6:
                                if (sublayer == SubLayer.NA) {
                                    land.getAbilities().add(ShroudAbility.getInstance());
                                }
                                break;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return Layer.AbilityAddingRemovingEffects_6 == layer;
    }

}

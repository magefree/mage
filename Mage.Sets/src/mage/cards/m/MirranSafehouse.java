package mage.cards.m;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author TheElk801
 */
public final class MirranSafehouse extends CardImpl {

    public MirranSafehouse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // As long as Mirran Safehouse is on the battlefield, it has all activated abilites of all land cards in all graveyards.
        this.addAbility(new SimpleStaticAbility(new MirranSafehouseEffect()));
    }

    private MirranSafehouse(final MirranSafehouse card) {
        super(card);
    }

    @Override
    public MirranSafehouse copy() {
        return new MirranSafehouse(this);
    }
}

class MirranSafehouseEffect extends ContinuousEffectImpl {

    MirranSafehouseEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "As long as {this} is on the battlefield, "
                + "it has all activated abilities of all land cards in all graveyards";
        this.dependendToTypes.add(DependencyType.AddingAbility); // Yixlid Jailer
    }

    private MirranSafehouseEffect(final MirranSafehouseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        Set<Ability> abilities = game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .map(graveyard -> graveyard.getCards(StaticFilters.FILTER_CARD_LAND, game))
                .flatMap(Collection::stream)
                .map(card -> card.getAbilities(game))
                .flatMap(Collection::stream)
                .filter(ActivatedAbility.class::isInstance)
                .collect(Collectors.toSet());
        for (Ability ability : abilities) {
            permanent.addAbility(ability, source.getSourceId(), game);
        }
        return true;
    }

    @Override
    public MirranSafehouseEffect copy() {
        return new MirranSafehouseEffect(this);
    }
}

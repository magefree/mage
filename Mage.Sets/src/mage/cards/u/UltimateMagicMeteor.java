package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UltimateMagicMeteor extends CardImpl {

    public UltimateMagicMeteor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}");

        // Ultimate Magic: Meteor deals 7 damage to each creature. If this spell was cast from exile, for each opponent, choose an artifact or land that player controls. Destroy the chosen permanents.
        this.getSpellAbility().addEffect(new DamageAllEffect(7, StaticFilters.FILTER_PERMANENT_CREATURE));
        this.getSpellAbility().addEffect(new UltimateMagicMeteorEffect());

        // Foretell {5}{R}
        this.addAbility(new ForetellAbility(this, "{5}{R}"));
    }

    private UltimateMagicMeteor(final UltimateMagicMeteor card) {
        super(card);
    }

    @Override
    public UltimateMagicMeteor copy() {
        return new UltimateMagicMeteor(this);
    }
}

class UltimateMagicMeteorEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("artifact or land");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    UltimateMagicMeteorEffect() {
        super(Outcome.Benefit);
        staticText = "If this spell was cast from exile, for each opponent, " +
                "choose an artifact or land that player controls. Destroy the chosen permanents";
    }

    private UltimateMagicMeteorEffect(final UltimateMagicMeteorEffect effect) {
        super(effect);
    }

    @Override
    public UltimateMagicMeteorEffect copy() {
        return new UltimateMagicMeteorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!Optional
                .ofNullable(source)
                .map(Ability::getSourceId)
                .map(game::getSpell)
                .map(Spell::getFromZone)
                .map(Zone.EXILED::match)
                .orElse(false)) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<Permanent> permanents = new HashSet<>();
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            FilterPermanent filter = this.filter.copy();
            Optional.ofNullable(playerId)
                    .map(game::getPlayer)
                    .map(Player::getName)
                    .ifPresent(s -> filter.setMessage("artifact or land controlled by " + s));
            filter.add(new ControllerIdPredicate(playerId));
            if (!game.getBattlefield().contains(filter, source.getControllerId(), source, game, 1)) {
                continue;
            }
            TargetPermanent target = new TargetPermanent(filter);
            target.withNotTarget(true);
            player.choose(outcome, target, source, game);
            permanents.add(game.getPermanent(target.getFirstTarget()));
        }
        for (Permanent permanent : permanents) {
            if (permanent != null) {
                permanent.destroy(source, game);
            }
        }
        return true;
    }
}

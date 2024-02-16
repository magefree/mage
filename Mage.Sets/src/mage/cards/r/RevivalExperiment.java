package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.CardTypeAssignment;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RevivalExperiment extends CardImpl {

    public RevivalExperiment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{G}");

        // For each permanent type, return up to one card of that type from your graveyard to the battlefield. You lose 3 life for each card returned this way. Exile Revival Experiment.
        this.getSpellAbility().addEffect(new RevivalExperimentEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private RevivalExperiment(final RevivalExperiment card) {
        super(card);
    }

    @Override
    public RevivalExperiment copy() {
        return new RevivalExperiment(this);
    }
}

class RevivalExperimentEffect extends OneShotEffect {

    RevivalExperimentEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "for each permanent type, return up to one card of that type from your graveyard " +
                "to the battlefield. You lose 3 life for each card returned this way.";
    }

    private RevivalExperimentEffect(final RevivalExperimentEffect effect) {
        super(effect);
    }

    @Override
    public RevivalExperimentEffect copy() {
        return new RevivalExperimentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        RevivalExperimentTarget target = new RevivalExperimentTarget();
        player.choose(outcome, target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        player.moveCards(cards, Zone.BATTLEFIELD, source, game);
        int toBattlefield = cards
                .stream()
                .map(game.getState()::getZone)
                .filter(Zone.BATTLEFIELD::equals)
                .mapToInt(x -> 1)
                .sum();
        player.loseLife(3 * toBattlefield, game, source, false);
        return true;
    }
}

class RevivalExperimentTarget extends TargetCardInYourGraveyard {

    private static final CardTypeAssignment cardTypeAssigner = new CardTypeAssignment(
            Arrays.stream(CardType.values())
                    .filter(CardType::isPermanentType)
                    .toArray(CardType[]::new)
    );

    RevivalExperimentTarget() {
        super(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_PERMANENT, true);
    }

    private RevivalExperimentTarget(final RevivalExperimentTarget target) {
        super(target);
    }

    @Override
    public RevivalExperimentTarget copy() {
        return new RevivalExperimentTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability ability, Game game) {
        if (!super.canTarget(playerId, id, ability, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(card);
        return cardTypeAssigner.getRoleCount(cards, game) >= cards.size();
    }


    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        possibleTargets.removeIf(uuid -> !this.canTarget(sourceControllerId, uuid, null, game));
        return possibleTargets;
    }
}

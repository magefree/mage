package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.CardTypeAssignment;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DruidicRitual extends CardImpl {

    public DruidicRitual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // You may mill three cards. Then return up to one creature card and up to one land card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new DruidicRitualEffect());
    }

    private DruidicRitual(final DruidicRitual card) {
        super(card);
    }

    @Override
    public DruidicRitual copy() {
        return new DruidicRitual(this);
    }
}

class DruidicRitualEffect extends OneShotEffect {

    DruidicRitualEffect() {
        super(Outcome.Benefit);
        staticText = "you may mill three cards. Then return up to one creature card " +
                "and up to one land card from your graveyard to your hand";
    }

    private DruidicRitualEffect(final DruidicRitualEffect effect) {
        super(effect);
    }

    @Override
    public DruidicRitualEffect copy() {
        return new DruidicRitualEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (player.chooseUse(outcome, "Mill three cards?", source, game)) {
            player.millCards(3, source, game);
        }
        TargetCard target = new RevivalExperimentTarget();
        player.choose(outcome, target, source, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
        return true;
    }
}

class RevivalExperimentTarget extends TargetCardInYourGraveyard {

    private static final CardTypeAssignment cardTypeAssigner = new CardTypeAssignment(CardType.CREATURE, CardType.LAND);

    RevivalExperimentTarget() {
        super(0, 2, StaticFilters.FILTER_CARD_CREATURE_OR_LAND, true);
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

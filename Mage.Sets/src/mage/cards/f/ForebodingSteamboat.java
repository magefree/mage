package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInExile;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class ForebodingSteamboat extends CardImpl {

    public ForebodingSteamboat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{B}{B}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // When Foreboding Steamboat enters, each player chooses two nontoken, non-Vehicle creatures they control. Exile them until Foreboding Steamboat leaves the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ForebodingSteamboatExileEffect()));

        // Whenever Foreboding Steamboat attacks, put a card exiled with it into its owner's graveyard. If you do, investigate.
        this.addAbility(new AttacksTriggeredAbility(new ForebodingSteamboatInvestigateEffect()));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private ForebodingSteamboat(final ForebodingSteamboat card) {
        super(card);
    }

    @Override
    public ForebodingSteamboat copy() {
        return new ForebodingSteamboat(this);
    }
}

class ForebodingSteamboatExileEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("nontoken, non-Vehicle creatures you control");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(Predicates.not(SubType.VEHICLE.getPredicate()));
    }

    ForebodingSteamboatExileEffect() {
        super(Outcome.Benefit);
        staticText = "each player chooses two nontoken, non-Vehicle creatures they control. " +
                "Exile them until {this} leaves the battlefield";
    }

    private ForebodingSteamboatExileEffect(final ForebodingSteamboatExileEffect effect) {
        super(effect);
    }

    @Override
    public ForebodingSteamboatExileEffect copy() {
        return new ForebodingSteamboatExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getSourcePermanentIfItStillExists(game) == null) {
            // if source permanent is already gone then nothing gets exiled in the first place
            return false;
        }
        Set<Permanent> toExile = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, playerId, source, game);
            if (permanents.size() <= 2) {
                toExile.addAll(permanents);
                continue;
            }
            TargetPermanent target = new TargetPermanent(2, filter);
            target.withNotTarget(true);
            player.choose(outcome, target, source, game);
            toExile.addAll(
                    target.getTargets()
                            .stream()
                            .map(game::getPermanent)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList())
            );
        }
        if (toExile.isEmpty()) {
            return false;
        }
        return !toExile.isEmpty()
                && new ExileUntilSourceLeavesEffect()
                .setTargetPointer(new FixedTargets(toExile, game))
                .apply(game, source);
    }
}

class ForebodingSteamboatInvestigateEffect extends OneShotEffect {

    ForebodingSteamboatInvestigateEffect() {
        super(Outcome.Benefit);
        staticText = "put a card exiled with it into its owner's graveyard. If you do, investigate";
    }

    private ForebodingSteamboatInvestigateEffect(final ForebodingSteamboatInvestigateEffect effect) {
        super(effect);
    }

    @Override
    public ForebodingSteamboatInvestigateEffect copy() {
        return new ForebodingSteamboatInvestigateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
        if (player == null || exileZone == null) {
            return false;
        }
        Card card;
        switch (exileZone.size()) {
            case 0:
                return false;
            case 1:
                card = exileZone.getRandom(game);
                break;
            default:
                TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD, exileZone.getId());
                target.withChooseHint("to put into its owner's graveyard");
                player.choose(outcome, target, source, game);
                card = game.getCard(target.getFirstTarget());
        }
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.GRAVEYARD, source, game);
        InvestigateEffect.doInvestigate(player.getId(), 1, game, source);
        return true;
    }
}

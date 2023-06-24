package mage.cards.u;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPlaneswalkerCard;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class UrzasSylex extends CardImpl {

    private static final FilterCard filter = new FilterPlaneswalkerCard();

    public UrzasSylex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);

        // {2}{W}{W}, {T}, Exile Urza's Sylex: Each player chooses six lands they control. Destroy all other permanents. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new UrzasSylexEffect(), new ManaCostsImpl<>("{2}{W}{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);

        // When Urza's Sylex is put into exile from the battlefield, you may pay {2}. If you do, search your library for a planeswalker card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new ZoneChangeTriggeredAbility(
                Zone.BATTLEFIELD, Zone.EXILED,
                new DoIfCostPaid(
                        new SearchLibraryPutInHandEffect(
                                new TargetCardInLibrary(filter), true
                        ), new GenericManaCost(2)
                ), "When {this} is put into exile from the battlefield, ", false
        ));
    }

    private UrzasSylex(final UrzasSylex card) {
        super(card);
    }

    @Override
    public UrzasSylex copy() {
        return new UrzasSylex(this);
    }
}

class UrzasSylexEffect extends OneShotEffect {

    UrzasSylexEffect() {
        super(Outcome.Benefit);
        staticText = "each player chooses six lands they control. Destroy all other permanents";
    }

    private UrzasSylexEffect(final UrzasSylexEffect effect) {
        super(effect);
    }

    @Override
    public UrzasSylexEffect copy() {
        return new UrzasSylexEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> toKeep = new HashSet<>();
        Map<UUID, Set<Permanent>> landMap = game
                .getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_LAND, source.getControllerId(), source, game)
                .stream()
                .collect(Collectors.groupingBy(
                        Controllable::getControllerId,
                        Collectors.toCollection(() -> new HashSet<>())
                ));
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            Set<Permanent> lands = landMap.getOrDefault(playerId, Collections.emptySet());
            if (lands.size() <= 6) {
                lands.stream()
                        .map(MageItem::getId)
                        .forEach(toKeep::add);
                continue;
            }
            TargetPermanent target = new TargetPermanent(6, StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND);
            target.setNotTarget(true);
            player.choose(outcome, target, source, game);
            toKeep.addAll(target.getTargets());
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT, source.getControllerId(), source, game
        )) {
            if (!toKeep.contains(permanent.getId())) {
                permanent.destroy(source, game);
            }
        }
        return true;
    }
}

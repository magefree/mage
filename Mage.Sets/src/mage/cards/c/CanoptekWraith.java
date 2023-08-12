package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterBasicLandCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class CanoptekWraith extends CardImpl {

    public CanoptekWraith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.WRAITH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Wraith Form -- Canoptek Wraith can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility().withFlavorWord("Wraith Form"));

        // Transdimensional Scout -- When Canoptek Wraith deals combat damage to a player, you may pay {3} and sacrifice it. If you do, choose a land you control. Then search your library for up to two basic land cards which have the same name as the chosen land, put them onto the battlefield tapped, then shuffle.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DoIfCostPaid(
                        new CanoptekWraithEffect(),
                        new CompositeCost(
                                new GenericManaCost(3), new SacrificeSourceCost(),
                                "pay {3} and sacrifice it"
                        )
                ), false
        ).setTriggerPhrase("When {this} deals combat damage to a player, ").withFlavorWord("Transdimensional Scout"));
    }

    private CanoptekWraith(final CanoptekWraith card) {
        super(card);
    }

    @Override
    public CanoptekWraith copy() {
        return new CanoptekWraith(this);
    }
}

class CanoptekWraithEffect extends OneShotEffect {

    CanoptekWraithEffect() {
        super(Outcome.Benefit);
        staticText = "choose a land you control. Then search your library for up to two basic land cards " +
                "which have the same name as the chosen land, put them onto the battlefield tapped, then shuffle";
    }

    private CanoptekWraithEffect(final CanoptekWraithEffect effect) {
        super(effect);
    }

    @Override
    public CanoptekWraithEffect copy() {
        return new CanoptekWraithEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, source, game, 1
        )) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        FilterCard filter = new FilterBasicLandCard("basic land cards with the same name as the chosen land");
        filter.add(new CanoptekWraithPredicate(permanent));
        TargetCardInLibrary targetCard = new TargetCardInLibrary(0, 2, filter);
        player.searchLibrary(targetCard, source, game);
        Set<Card> cards = targetCard
                .getTargets()
                .stream()
                .map(uuid -> player.getLibrary().getCard(uuid, game))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        player.moveCards(cards, Zone.BATTLEFIELD, source, game, true, false, false, null);
        player.shuffleLibrary(source, game);
        return true;
    }
}

class CanoptekWraithPredicate implements Predicate<Card> {

    private final Permanent permanent;

    CanoptekWraithPredicate(Permanent permanent) {
        this.permanent = permanent;
    }

    @Override
    public boolean apply(Card input, Game game) {
        return CardUtil.haveSameNames(permanent, input);
    }
}
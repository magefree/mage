package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;
import mage.util.RandomUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SotheraTheSupervoid extends CardImpl {

    public SotheraTheSupervoid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);

        // Whenever a creature you control dies, each opponent chooses a creature they control and exiles it.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new SotheraTheSupervoidExileEffect(), false,
                StaticFilters.FILTER_CONTROLLED_A_CREATURE
        ));

        // At the beginning of your end step, if a player controls no creatures, sacrifice Sothera, then put a creature card exiled with it onto the battlefield under your control with two additional +1/+1 counters on it.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new SacrificeSourceEffect())
                .withInterveningIf(SotheraTheSupervoidCondition.instance);
        ability.addEffect(new SotheraTheSupervoidReturnEffect());
        this.addAbility(ability);
    }

    private SotheraTheSupervoid(final SotheraTheSupervoid card) {
        super(card);
    }

    @Override
    public SotheraTheSupervoid copy() {
        return new SotheraTheSupervoid(this);
    }
}

enum SotheraTheSupervoidCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .anyMatch(playerId -> !game.getBattlefield().contains(
                        StaticFilters.FILTER_CONTROLLED_CREATURE, playerId, source, game, 1
                ));
    }

    @Override
    public String toString() {
        return "a player controls no creatures";
    }
}

class SotheraTheSupervoidExileEffect extends OneShotEffect {

    SotheraTheSupervoidExileEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent chooses a creature they control and exiles it";
    }

    private SotheraTheSupervoidExileEffect(final SotheraTheSupervoidExileEffect effect) {
        super(effect);
    }

    @Override
    public SotheraTheSupervoidExileEffect copy() {
        return new SotheraTheSupervoidExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player == null || !game.getBattlefield().contains(
                    StaticFilters.FILTER_CONTROLLED_CREATURE, playerId, source, game, 1
            )) {
                return false;
            }
            TargetPermanent target = new TargetControlledCreaturePermanent();
            target.withChooseHint("to exile");
            target.withNotTarget(true);
            player.choose(Outcome.Exile, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent == null) {
                continue;
            }
            player.moveCardsToExile(
                    permanent, source, game, true,
                    CardUtil.getExileZoneId(game, source),
                    CardUtil.getSourceName(game, source)
            );
        }
        return true;
    }
}

class SotheraTheSupervoidReturnEffect extends OneShotEffect {

    SotheraTheSupervoidReturnEffect() {
        super(Outcome.Benefit);
        staticText = ", then put a creature card exiled with it onto the battlefield " +
                "under your control with two additional +1/+1 counters on it";
    }

    private SotheraTheSupervoidReturnEffect(final SotheraTheSupervoidReturnEffect effect) {
        super(effect);
    }

    @Override
    public SotheraTheSupervoidReturnEffect copy() {
        return new SotheraTheSupervoidReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (player == null || exileZone == null) {
            return false;
        }
        Card card;
        switch (exileZone.count(StaticFilters.FILTER_CARD_CREATURE, game)) {
            case 0:
                return false;
            case 1:
                card = RandomUtil.randomFromCollection(exileZone.getCards(StaticFilters.FILTER_CARD_CREATURE, game));
                break;
            default:
                TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD_CREATURE, exileZone.getId());
                player.choose(outcome, target, source, game);
                card = game.getCard(target.getFirstTarget());
        }
        if (card == null) {
            return false;
        }
        game.setEnterWithCounters(card.getId(), new Counters(CounterType.P1P1.createInstance(2)));
        return player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}

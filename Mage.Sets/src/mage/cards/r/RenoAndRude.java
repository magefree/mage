package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RenoAndRude extends CardImpl {

    public RenoAndRude(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Reno and Rude deals combat damage to a player, exile the top card of that player's library. Then you may sacrifice another creature or artifact. If you do, you may play the exiled card this turn, and mana of any type can be spent to cast it.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new RenoAndRudeEffect(), false, true
        ));
    }

    private RenoAndRude(final RenoAndRude card) {
        super(card);
    }

    @Override
    public RenoAndRude copy() {
        return new RenoAndRude(this);
    }
}

class RenoAndRudeEffect extends OneShotEffect {

    RenoAndRudeEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of that player's library. " +
                "Then you may sacrifice another creature or artifact. If you do, " +
                "you may play the exiled card this turn, and mana of any type can be spent to cast it";
    }

    private RenoAndRudeEffect(final RenoAndRudeEffect effect) {
        super(effect);
    }

    @Override
    public RenoAndRudeEffect copy() {
        return new RenoAndRudeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = Optional
                .ofNullable(getTargetPointer().getFirst(game, source))
                .map(game::getPlayer)
                .map(Player::getLibrary)
                .map(library -> library.getFromTop(game))
                .orElse(null);
        Cost cost = new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ARTIFACT_OR_OTHER_CREATURE);
        if (card == null) {
            return cost.canPay(source, source, source.getControllerId(), game)
                    && player.chooseUse(Outcome.DrawCard, "Sacrifice another creature or artifact?", source, game)
                    && cost.pay(source, game, source, player.getId(), true);
        }
        player.moveCards(card, Zone.EXILED, source, game);
        if (cost.canPay(source, source, source.getControllerId(), game)
                && player.chooseUse(Outcome.DrawCard, "Sacrifice another creature or artifact?", source, game)
                && cost.pay(source, game, source, player.getId(), true)) {
            CardUtil.makeCardPlayable(game, source, card, false, Duration.EndOfTurn, true);
        }
        return true;
    }
}

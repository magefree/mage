package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CommanderLiaraPortyr extends CardImpl {

    public CommanderLiaraPortyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Whenever you attack, spells you cast from exile this turn cost {X} less to cast, where X is the number of players being attacked. Exile the top X cards of your library. Until end of turn, you may cast spells from among those exiled cards.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new CommanderLiaraPortyrCostEffect(), 1);
        ability.addEffect(new CommanderLiaraPortyrExileEffect());
        this.addAbility(ability);
    }

    private CommanderLiaraPortyr(final CommanderLiaraPortyr card) {
        super(card);
    }

    @Override
    public CommanderLiaraPortyr copy() {
        return new CommanderLiaraPortyr(this);
    }
}

class CommanderLiaraPortyrCostEffect extends CostModificationEffectImpl {

    CommanderLiaraPortyrCostEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "spells you cast from exile this turn cost {X} less to cast, " +
                "where X is the number of players being attacked";
    }

    private CommanderLiaraPortyrCostEffect(final CommanderLiaraPortyrCostEffect effect) {
        super(effect);
    }

    @Override
    public CommanderLiaraPortyrCostEffect copy() {
        return new CommanderLiaraPortyrCostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Optional.ofNullable((Integer) getValue(AttacksWithCreaturesTriggeredAbility.VALUEKEY_NUMBER_DEFENDING_PLAYERS))
                .ifPresent(i -> CardUtil.reduceCost(abilityToModify, 1));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return Optional
                .ofNullable(abilityToModify)
                .map(Ability::getSourceId)
                .map(game::getSpell)
                .map(Spell::getFromZone)
                .filter(Zone.EXILED::match)
                .isPresent();
    }
}

class CommanderLiaraPortyrExileEffect extends OneShotEffect {

    CommanderLiaraPortyrExileEffect() {
        super(Outcome.Benefit);
        staticText = "Exile the top X cards of your library. Until end of turn, " +
                "you may cast spells from among those exiled cards";
    }

    private CommanderLiaraPortyrExileEffect(final CommanderLiaraPortyrExileEffect effect) {
        super(effect);
    }

    @Override
    public CommanderLiaraPortyrExileEffect copy() {
        return new CommanderLiaraPortyrExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int count = Optional.ofNullable((Integer) getValue(
                AttacksWithCreaturesTriggeredAbility.VALUEKEY_NUMBER_DEFENDING_PLAYERS
        )).orElse(0);
        if (player == null || count < 1) {
            return true;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, count));
        if (cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        for (Card card : cards.getCards(game)) {
            CardUtil.makeCardPlayable(game, source, card, true, Duration.EndOfTurn, false);
        }
        return true;
    }
}

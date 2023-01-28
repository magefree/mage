package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Grath
 */
public final class SolphimMayhemDominus extends CardImpl {

    public SolphimMayhemDominus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // If a source you control would deal noncombat damage to an opponent or a permanent an opponent controls, it deals double that damage to that player or permanent instead.
        this.addAbility(new SimpleStaticAbility(new SolphimMayhemDominusEffect()));

        // {1}{R/P}{R/P}, Discard two cards: Put an indestructible counter on Solphim, Mayhem Dominus.
        Ability ability = new SimpleActivatedAbility(new AddCountersSourceEffect(CounterType.INDESTRUCTIBLE.createInstance()), new ManaCostsImpl<>("{1}{R/P}{R/P}"));
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(2, new FilterCard("two cards"))));
        this.addAbility(ability);
    }

    private SolphimMayhemDominus(final SolphimMayhemDominus card) {
        super(card);
    }

    @Override
    public SolphimMayhemDominus copy() {
        return new SolphimMayhemDominus(this);
    }
}

class SolphimMayhemDominusEffect extends ReplacementEffectImpl {

    SolphimMayhemDominusEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if a source you control would deal noncombat damage to a permanent or player, " +
                "it deals double that damage that permanent or player instead";
    }

    SolphimMayhemDominusEffect(final SolphimMayhemDominusEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return source.isControlledBy(game.getControllerId(event.getSourceId()))
                && !((DamageEvent) event).isCombatDamage()
                && (player.hasOpponent(event.getTargetId(), game)
                    || player.hasOpponent(game.getControllerId(event.getTargetId()), game));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), event.getAmount()));
        return false;
    }

    @Override
    public SolphimMayhemDominusEffect copy() {
        return new SolphimMayhemDominusEffect(this);
    }
}

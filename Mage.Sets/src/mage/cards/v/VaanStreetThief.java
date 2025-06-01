package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VaanStreetThief extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("Scouts, Pirates, and/or Rogues you control");
    private static final FilterSpell filter2 = new FilterSpell("a spell you don't own");

    static {
        filter.add(Predicates.or(
                SubType.SCOUT.getPredicate(),
                SubType.PIRATE.getPredicate(),
                SubType.ROGUE.getPredicate()
        ));
        filter2.add(TargetController.NOT_YOU.getOwnerPredicate());
    }

    public VaanStreetThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever one or more Scouts, Pirates, and/or Rogues you control deal combat damage to a player, exile the top card of that player's library. You may cast it. If you don't, create a Treasure token.
        this.addAbility(new OneOrMoreCombatDamagePlayerTriggeredAbility(
                new VaanStreetThiefEffect(), SetTargetPointer.PLAYER, filter, false
        ));

        // Whenever you cast a spell you don't own, put a +1/+1 counter on each Scout, Pirate, and Rogue you control.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)
                        .setText("put a +1/+1 counter on each Scout, Pirate, and Rogue you control"),
                filter2, false
        ));
    }

    private VaanStreetThief(final VaanStreetThief card) {
        super(card);
    }

    @Override
    public VaanStreetThief copy() {
        return new VaanStreetThief(this);
    }
}

class VaanStreetThiefEffect extends OneShotEffect {

    VaanStreetThiefEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of that player's library. " +
                "You may cast it. If you don't, create a Treasure token";
    }

    private VaanStreetThiefEffect(final VaanStreetThiefEffect effect) {
        super(effect);
    }

    @Override
    public VaanStreetThiefEffect copy() {
        return new VaanStreetThiefEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        controller.moveCards(card, Zone.EXILED, source, game);
        if (!controller.chooseUse(Outcome.DrawCard, "Cast " + card.getIdName() + '?', source, game)
                || !CardUtil.castSingle(controller, source, game, card)) {
            new TreasureToken().putOntoBattlefield(1, game, source);
        }
        return true;
    }
}

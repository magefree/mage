package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.*;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author Grath
 */
public final class KastralTheWindcrested extends CardImpl {

    public static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.BIRD, "Birds you control");
    public static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent(SubType.BIRD, "Bird you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public KastralTheWindcrested(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever one or more Birds you control deal combat damage to a player, choose one --
        // * You may put a Bird creature card from your hand or graveyard onto the battlefield with a finality counter on it.
        Ability ability = new OneOrMoreCombatDamagePlayerTriggeredAbility(Zone.BATTLEFIELD,
                new KastralTheWindcrestedEffect(), filter, SetTargetPointer.NONE, false);

        // * Put a +1/+1 counter on each Bird you control.
        ability.addMode(new Mode(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter2)));

        // * Draw a card.
        ability.addMode(new Mode(new DrawCardSourceControllerEffect(1)));

        this.addAbility(ability);

    }

    private KastralTheWindcrested(final KastralTheWindcrested card) {
        super(card);
    }

    @Override
    public KastralTheWindcrested copy() {
        return new KastralTheWindcrested(this);
    }
}

class KastralTheWindcrestedEffect extends OneShotEffect {
    public static final FilterCreatureCard filter = new FilterCreatureCard("Bird");

    static {
        filter.add(SubType.BIRD.getPredicate());
    }

    public KastralTheWindcrestedEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "You may put a Bird creature card from your hand or graveyard onto the battlefield with " +
                "a finality counter on it.";
    }

    protected KastralTheWindcrestedEffect(final KastralTheWindcrestedEffect effect) {
        super(effect);
    }

    @Override
    public KastralTheWindcrestedEffect copy() {
        return new KastralTheWindcrestedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        cards.addAllCards(controller.getHand().getCards(filter, source.getControllerId(), source, game));
        cards.addAllCards(controller.getGraveyard().getCards(filter, source.getControllerId(), source, game));
        if (cards.isEmpty()) {
            return true;
        }
        TargetCard target = new TargetCard(1, 1, Zone.ALL, filter);
        target.withNotTarget(true);
        controller.choose(outcome, cards, target, source, game);
        for (UUID targetId : target.getTargets()) {
            game.setEnterWithCounters(targetId, new Counters().addCounter(CounterType.FINALITY.createInstance()));
        }
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, false, null);
        }
        return true;
    }
}

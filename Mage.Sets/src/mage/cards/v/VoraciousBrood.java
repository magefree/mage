package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class VoraciousBrood extends CardImpl {

    public VoraciousBrood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // This creature enters with a +1/+1 counter on it for each creature card in your graveyard.
        this.addAbility(new EntersBattlefieldAbility(new VoraciousBroodEffect(), "with a +1/+1 counter on it for each creature card in your graveyard"));

        // Whenever one or more creature cards are put into your graveyard from anywhere, put that many +1/+1 counters on this creature.
        this.addAbility(new VoraciousBroodAbility());
    }

    private VoraciousBrood(final VoraciousBrood card) {
        super(card);
    }

    @Override
    public VoraciousBrood copy() {
        return new VoraciousBrood(this);
    }
}

class VoraciousBroodEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard();

    public VoraciousBroodEffect() {
        super(Outcome.BoostCreature);
        staticText = "{this} enters with a +1/+1 counter on it for each creature card in your graveyard";
    }

    private VoraciousBroodEffect(final VoraciousBroodEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent != null && player != null) {
            int amount = 0;
            amount += player.getGraveyard().count(filter, game);
            if (amount > 0) {
                permanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public VoraciousBroodEffect copy() {
        return new VoraciousBroodEffect(this);
    }

}

class VoraciousBroodAbility extends TriggeredAbilityImpl {

    VoraciousBroodAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
    }

    private VoraciousBroodAbility(final VoraciousBroodAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        if (zEvent != null
            && Zone.GRAVEYARD == zEvent.getToZone()
            && zEvent.getCards() != null) {
            int cardCount = 0;
            for (Card card : zEvent.getCards()) {
                if (card != null && card.isOwnedBy(getControllerId()) && card.isCreature(game)) {
                    cardCount++;
                }
            }
            if (cardCount == 0) {
                return false;
            }
            this.getEffects().clear();
            this.getEffects().add(new AddCountersSourceEffect(CounterType.P1P1.createInstance(cardCount)));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creature cards are put into your graveyard from anywhere, put that many +1/+1 counters on {this}.";
    }

    @Override
    public VoraciousBroodAbility copy() {
        return new VoraciousBroodAbility(this);
    }
}

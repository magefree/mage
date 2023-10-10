package mage.cards.f;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author weirddan455
 */
public final class Froghemoth extends CardImpl {

    public Froghemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Froghemoth deals combat damage to a player, exile up to that many target cards from their graveyard.
        // Put a +1/+1 counter on Froghemoth for each creature exiled this way. You gain 1 life for each noncreature card exiled this way.
        this.addAbility(new FroghemothTriggeredAbility());
    }

    private Froghemoth(final Froghemoth card) {
        super(card);
    }

    @Override
    public Froghemoth copy() {
        return new Froghemoth(this);
    }
}

class FroghemothTriggeredAbility extends DealsCombatDamageToAPlayerTriggeredAbility {

    public FroghemothTriggeredAbility() {
        super(new FroghemothEffect(), false);
    }

    private FroghemothTriggeredAbility(final FroghemothTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FroghemothTriggeredAbility copy() {
        return new FroghemothTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            FilterCard filter = new FilterCard("cards from defender's graveyard");
            filter.add(new OwnerIdPredicate(event.getPlayerId()));
            this.getTargets().clear();
            this.addTarget(new TargetCardInGraveyard(0, event.getAmount(), filter));
            return true;
        }
        return false;
    }
}

class FroghemothEffect extends OneShotEffect {

    public FroghemothEffect() {
        super(Outcome.Exile);
        this.staticText = "exile up to that many target cards from their graveyard. Put a +1/+1 counter on {this} for each creature card exiled this way. You gain 1 life for each noncreature card exiled this way";
    }

    private FroghemothEffect(final FroghemothEffect effect) {
        super(effect);
    }

    @Override
    public FroghemothEffect copy() {
        return new FroghemothEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> cardsToExile = new HashSet<>();
        int numCounters = 0;
        int lifeGain = 0;
        for (UUID cardId : getTargetPointer().getTargets(game, source)) {
            Card card = game.getCard(cardId);
            if (card != null && game.getState().getZone(cardId) == Zone.GRAVEYARD && cardsToExile.add(card)) {
                if (card.isCreature(game)) {
                    numCounters++;
                } else {
                    lifeGain++;
                }
            }
        }
        if (!cardsToExile.isEmpty()) {
            controller.moveCards(cardsToExile, Zone.EXILED, source, game);
            game.getState().processAction(game);
            if (numCounters > 0) {
                Permanent permanent = source.getSourcePermanentIfItStillExists(game);
                if (permanent != null) {
                    permanent.addCounters(CounterType.P1P1.createInstance(numCounters), source.getControllerId(), source, game);
                }
            }
            if (lifeGain > 0) {
                controller.gainLife(lifeGain, game, source);
            }
        }
        return true;
    }
}

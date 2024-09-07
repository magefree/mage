package mage.cards.o;

import java.util.UUID;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.ForageCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.counter.AddCounterEnteringCreatureEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author Grath
 */
public final class OsteomancerAdept extends CardImpl {

    public OsteomancerAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        
        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // {T}: Until end of turn, you may cast creature spells from your graveyard by foraging in addition to paying their other costs. If you cast a spell this way, that creature enters with a finality counter on it.
        this.addAbility(new SimpleActivatedAbility(new OsteomancerAdeptEffect(), new TapSourceCost()).setIdentifier(MageIdentifier.OsteomancerAdeptAlternateCast), new OsteomancerAdeptWatcher());
    }

    private OsteomancerAdept(final OsteomancerAdept card) {
        super(card);
    }

    @Override
    public OsteomancerAdept copy() {
        return new OsteomancerAdept(this);
    }
}

class OsteomancerAdeptEffect extends AsThoughEffectImpl {

    OsteomancerAdeptEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.AIDontUseIt);
        staticText = "until end of turn, you may cast creature spells from your graveyard by foraging in addition to " +
                "paying their other costs. If you cast a spell this way, that creature enters with a finality " +
                "counter on it.";
    }

    private OsteomancerAdeptEffect(final OsteomancerAdeptEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public OsteomancerAdeptEffect copy() {
        return new OsteomancerAdeptEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId)) {
            return false;
        }
        Card card = game.getCard(objectId);
        Player player = game.getPlayer(affectedControllerId);
        if (card == null
                || player == null
                || !card.isOwnedBy(affectedControllerId)
                || !card.isCreature(game)
                || !game.getState().getZone(objectId).match(Zone.GRAVEYARD)) {
            return false;
        }
        Costs<Cost> newCosts = new CostsImpl<>();
        newCosts.addAll(card.getSpellAbility().getCosts());
        newCosts.add(new ForageCost());
        player.setCastSourceIdWithAlternateMana(
                card.getId(), card.getManaCost(), newCosts,
                MageIdentifier.OsteomancerAdeptAlternateCast
        );
        return true;
    }
}

class OsteomancerAdeptWatcher extends Watcher {
    OsteomancerAdeptWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (GameEvent.EventType.SPELL_CAST.equals(event.getType())
                && event.hasApprovingIdentifier(MageIdentifier.OsteomancerAdeptAlternateCast)) {
            Spell target = game.getSpell(event.getTargetId());
            if (target != null) {
                game.getState().addEffect(new AddCounterEnteringCreatureEffect(new MageObjectReference(target.getCard(), game),
                                CounterType.FINALITY.createInstance(), Outcome.UnboostCreature),
                        target.getSpellAbility());
            }
        }
    }
}

package mage.cards.n;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.counter.AddCounterEnteringCreatureEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NoctisPrinceOfLucis extends CardImpl {

    public NoctisPrinceOfLucis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // You may cast artifact spells from your graveyard by paying 3 life in addition to paying their other costs. If you cast a spell this way, that artifact enters with a finality counter on it.
        this.addAbility(new SimpleStaticAbility(new NoctisPrinceOfLucisEffect())
                .setIdentifier(MageIdentifier.NoctisPrinceOfLucisAlternateCast), new NoctisPrinceOfLucisWatcher());
    }

    private NoctisPrinceOfLucis(final NoctisPrinceOfLucis card) {
        super(card);
    }

    @Override
    public NoctisPrinceOfLucis copy() {
        return new NoctisPrinceOfLucis(this);
    }
}

class NoctisPrinceOfLucisEffect extends AsThoughEffectImpl {

    NoctisPrinceOfLucisEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.AIDontUseIt);
        staticText = "you may cast artifact spells from your graveyard by paying 3 life in addition to paying their " +
                "other costs. If you cast a spell this way, that artifact enters with a finality counter on it";
    }

    private NoctisPrinceOfLucisEffect(final NoctisPrinceOfLucisEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public NoctisPrinceOfLucisEffect copy() {
        return new NoctisPrinceOfLucisEffect(this);
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
                || !card.isArtifact(game)
                || !game.getState().getZone(objectId).match(Zone.GRAVEYARD)) {
            return false;
        }
        Costs<Cost> newCosts = new CostsImpl<>();
        newCosts.addAll(card.getSpellAbility().getCosts());
        newCosts.add(new PayLifeCost(3));
        player.setCastSourceIdWithAlternateMana(
                card.getId(), card.getManaCost(), newCosts,
                MageIdentifier.NoctisPrinceOfLucisAlternateCast
        );
        return true;
    }
}

class NoctisPrinceOfLucisWatcher extends Watcher {

    NoctisPrinceOfLucisWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (!GameEvent.EventType.SPELL_CAST.equals(event.getType())
                || !event.hasApprovingIdentifier(MageIdentifier.NoctisPrinceOfLucisAlternateCast)) {
            return;
        }
        Spell target = game.getSpell(event.getTargetId());
        if (target != null) {
            game.getState().addEffect(new AddCounterEnteringCreatureEffect(
                    new MageObjectReference(target.getCard(), game),
                    CounterType.FINALITY.createInstance(), Outcome.UnboostCreature
            ), target.getSpellAbility());
        }
    }
}

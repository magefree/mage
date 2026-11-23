package mage.cards.o;

import java.util.UUID;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.OptionalAdditionalSourceCosts;
import mage.abilities.costs.common.ForageCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.counter.AddCounterEnteringCreatureEffect;
import mage.abilities.mana.BasicManaAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
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
        SpellAbility spell = card.getSpellAbility();
        // The card being cast leaves the graveyard before forage is paid.
        if (game.inCheckPlayableState() && player.getGraveyard().size() <= 3
                && !new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_FOOD).canPay(spell, spell, affectedControllerId, game)
                && !couldSupplyForageDuringCasting(card, player, game)) {
            return false;
        }
        Costs<Cost> newCosts = new CostsImpl<>();
        newCosts.addAll(spell.getCosts());
        newCosts.add(new ForageCost());
        player.setCastSourceIdWithAlternateMana(
                card.getId(), card.getManaCost(), newCosts,
                MageIdentifier.OsteomancerAdeptAlternateCast
        );
        return true;
    }

    private static boolean couldSupplyForageDuringCasting(Card card, Player player, Game game) {
        // Another cost, such as sacrificing a creature, can be paid before forage.
        SpellAbility spell = card.getSpellAbility().copy();
        spell.adjustX(game);
        game.getContinuousEffects().costModification(spell, game);
        if (!spell.getCosts().isEmpty()
                || card.getAbilities(game).stream().anyMatch(OptionalAdditionalSourceCosts.class::isInstance)) {
            return true;
        }
        // A mana ability can also put cards into the graveyard (e.g. Blood Pet).
        for (Card inHand : player.getHand().getCards(game)) {
            if (!inHand.getAbilities(game).getActivatedManaAbilities(Zone.HAND).isEmpty()) {
                return true;
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(player.getId(), game)) {
            if (permanent.getAbilities(game).stream().anyMatch(TriggeredManaAbility.class::isInstance)
                    || permanent.getAbilities(game).getAvailableActivatedManaAbilities(Zone.BATTLEFIELD, player.getId(), game)
                            .stream().anyMatch(ability -> !(ability instanceof BasicManaAbility))) {
                return true;
            }
        }
        return false;
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

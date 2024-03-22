package mage.cards.a;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.*;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.ManaValueLessThanOrEqualToSourcePowerPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;
import mage.watchers.common.CardsAmountDrawnThisTurnWatcher;
import mage.watchers.common.CastFromGraveyardWatcher;
import mage.watchers.common.OnceEachTurnCastWatcher;

/**
 *
 * @author justinjohnson14
 */
public final class ArcadeGannon extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.QUEST);

    static{
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                SubType.HUMAN.getPredicate()
        ));
    }

    public ArcadeGannon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Draw a card, then discard a card. Put a quest counter on Arcade Gannon.
        Ability ability = (new SimpleActivatedAbility(new DrawDiscardControllerEffect(1,1), new TapSourceCost()));
        ability.addEffect(new AddCountersSourceEffect(CounterType.QUEST.createInstance(1)));
        this.addAbility(ability);
        // For Auld Lang Syne -- Once during each of your turns, you may cast an artifact or Human spell from your graveyard with mana value less than or equal to the number of quest counters on Arcade Gannon.
        this.addAbility(
                new SimpleStaticAbility(new ArcadeGannonEffect(filter))
                        .setIdentifier(MageIdentifier.OnceEachTurnCastWatcher)
                        .addHint(OnceEachTurnCastWatcher.getHint()),
                new OnceEachTurnCastWatcher()
        );
    }

    private ArcadeGannon(final ArcadeGannon card) {
        super(card);
    }

    @Override
    public ArcadeGannon copy() {
        return new ArcadeGannon(this);
    }
}

class ArcadeGannonEffect extends AsThoughEffectImpl {

    private final FilterCard filter;

    ArcadeGannonEffect(FilterCard filter) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        this.filter = filter;
        this.staticText = "<i>For Auld Lang Syne </i>Once during each of your turns, you may cast " + filter.getMessage() + " from your graveyard with mana value less than or equal to the number of quest counters on Arcade Gannon.";
    }

    private ArcadeGannonEffect(final ArcadeGannonEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public ArcadeGannonEffect copy() {
        return new ArcadeGannonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        throw new IllegalArgumentException("Wrong code usage: can't call applies method on empty affectedAbility");
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (controller == null || sourcePermanent == null) {
            return false;
        }
        if (game.isActivePlayer(playerId) // only during your turn
                && source.isControlledBy(playerId) // only you may cast
                && Zone.GRAVEYARD.equals(game.getState().getZone(objectId)) // from graveyard
                && affectedAbility instanceof SpellAbility // characteristics to check
        ) {
            SpellAbility spellAbility = (SpellAbility) affectedAbility;
            Card cardToCheck = spellAbility.getCharacteristics(game);
            if (spellAbility.getManaCosts().isEmpty() || spellAbility.getCharacteristics(game).getManaValue() > game.getPermanent(source.getSourceId()).getCounters(game).getCount(CounterType.QUEST)) {
                return false;
            }
            return spellAbility.spellCanBeActivatedRegularlyNow(playerId, game)
                    && filter.match(cardToCheck, playerId, source, game);
        }
        return false;
    }
}

class CastFromGraveyardOnceWatcher extends Watcher {

    private final Set<MageObjectReference> usedFrom = new HashSet<>();

    CastFromGraveyardOnceWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (GameEvent.EventType.SPELL_CAST.equals(event.getType())
                && event.hasApprovingIdentifier(MageIdentifier.CastFromGraveyardOnceWatcher)) {
            usedFrom.add(event.getAdditionalReference().getApprovingMageObjectReference());
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedFrom.clear();
    }

    boolean abilityNotUsed(MageObjectReference mor) {
        return !usedFrom.contains(mor);
    }
}
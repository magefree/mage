package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.ShardToken;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.Watcher;
import mage.watchers.common.CardsDrawnThisTurnWatcher;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NikoAris extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.instance);
    }

    private static final DynamicValue xValue = new MultipliedValue(CardsDrawnThisTurnDynamicValue.instance, 2);

    public NikoAris(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{X}{W}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NIKO);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(3));

        // When Niko Aris enters the battlefield, create X Shard tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new ShardToken(), NikoArisValue.instance)
        ), new NikoArisWatcher());

        // +1: Up to one target creature you control can't be blocked this turn. Whenever that creature deals damage this turn, return it to its owner's hand.
        Ability ability = new LoyaltyAbility(new CantBeBlockedTargetEffect(Duration.EndOfTurn), 1);
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new NikoArisDamageTriggeredAbility()));
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.addAbility(ability);

        // −1: Niko Aris deals 2 damage to target tapped creature for each card you've drawn this turn.
        ability = new LoyaltyAbility(new DamageTargetEffect(xValue).setText(
                "{this} deals 2 damage to target tapped creature for each card you've drawn this turn"
        ), -1);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability, new CardsDrawnThisTurnWatcher());

        // −1: Create a Shard token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new ShardToken()), -1));
    }

    private NikoAris(final NikoAris card) {
        super(card);
    }

    @Override
    public NikoAris copy() {
        return new NikoAris(this);
    }
}

enum NikoArisValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        NikoArisWatcher watcher = game.getState().getWatcher(NikoArisWatcher.class, sourceAbility.getSourceId());
        if (watcher == null) {
            return 0;
        }
        if (game.getState().getValue(sourceAbility.getSourceId().toString() + "xValue") == null) {
            return 0;
        }
        return (Integer) game.getState().getValue(sourceAbility.getSourceId().toString() + "xValue");
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class NikoArisWatcher extends Watcher {

    NikoArisWatcher() {
        super(WatcherScope.CARD);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpellOrLKIStack(event.getTargetId());
        if (spell == null || spell.getSourceId() != super.getSourceId()) {
            return;
        }
        game.getState().setValue(
                spell.getSourceId().toString() + "xValue",
                spell.getSpellAbility().getManaCostsToPay().getX()
        );
    }
}

class NikoArisDamageTriggeredAbility extends DelayedTriggeredAbility {

    NikoArisDamageTriggeredAbility() {
        super(new ReturnToHandTargetEffect(), Duration.EndOfTurn, false);
    }

    private NikoArisDamageTriggeredAbility(final NikoArisDamageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NikoArisDamageTriggeredAbility copy() {
        return new NikoArisDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGED_PLAYER:
            case DAMAGED_PERMANENT:
                return true;
        }
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return this.getEffects()
                .stream()
                .filter(Objects::nonNull)
                .map(Effect::getTargetPointer)
                .map(targetPointer -> targetPointer.getFirst(game, this))
                .anyMatch(event.getSourceId()::equals);
    }

    @Override
    public String getRule() {
        return "Whenever that creature deals damage this turn, return it to its owner's hand.";
    }
}
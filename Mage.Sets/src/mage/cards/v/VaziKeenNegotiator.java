package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.TreasureSpentToCastCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.TreasureToken;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.watchers.common.CreatedTokenWatcher;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class VaziKeenNegotiator extends CardImpl {

    public VaziKeenNegotiator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.HUMAN, SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {T}: Target opponent creates X Treasure tokens, where X is the number of Treasure tokens you created this turn.
        Ability tapAbility = new SimpleActivatedAbility(new CreateTokenTargetEffect(new TreasureToken(), VaziKeenNegotiatorNumberOfTokensCreated.instance), new TapSourceCost());
        tapAbility.addTarget(new TargetOpponent());
        tapAbility.addWatcher(new CreatedTokenWatcher());
        this.addAbility(tapAbility);

        // Whenever an opponent casts a spell or activates an ability,
        // if mana from a Treasure was spent to cast it or activate it,
        // put a +1/+1 counter on target creature,
        // then draw a card.
        this.addAbility(new VaziKeenNegotiatorOpponentCastsOrActivatesTriggeredAbility());
    }

    private VaziKeenNegotiator(final VaziKeenNegotiator card) {
        super(card);
    }

    @Override
    public VaziKeenNegotiator copy() {
        return new VaziKeenNegotiator(this);
    }
}

class VaziKeenNegotiatorOpponentCastsOrActivatesTriggeredAbility extends TriggeredAbilityImpl {

    VaziKeenNegotiatorOpponentCastsOrActivatesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        addTarget(new TargetCreaturePermanent());
        addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        setTriggerPhrase("Whenever an opponent casts a spell or activates an ability, if mana from a Treasure was spent to cast it or activate it, ");
    }

    private VaziKeenNegotiatorOpponentCastsOrActivatesTriggeredAbility(final VaziKeenNegotiatorOpponentCastsOrActivatesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST
                || event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(getControllerId());
        Player caster = game.getPlayer(event.getPlayerId());
        if (controller == null
                || caster == null
                || !game.getOpponents(controller.getId()).contains(caster.getId())) {
            return false;
        }

        Ability ability = game.getStack().getStackObject(event.getTargetId()).getStackAbility();

        return TreasureSpentToCastCondition.instance.apply(game, ability);
    }

    @Override
    public TriggeredAbility copy() {
        return new VaziKeenNegotiatorOpponentCastsOrActivatesTriggeredAbility(this);
    }
}

enum VaziKeenNegotiatorNumberOfTokensCreated implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return CreatedTokenWatcher.getTypeCreatedCountByPlayer(sourceAbility.getControllerId(), SubType.TREASURE, game);
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "the number of Treasure tokens you created this turn";
    }

    @Override
    public String toString() {
        return "X";
    }
}

package mage.cards.u;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class UlamogTheDefiler extends CardImpl {

    public UlamogTheDefiler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{10}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // When you cast this spell, target opponent exiles half their library, rounded up.
        Ability ability = new CastSourceTriggeredAbility(new UlamogTheDefilerTargetEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Ward--Sacrifice two permanents.
        this.addAbility(new WardAbility(new SacrificeTargetCost(2, StaticFilters.FILTER_PERMANENTS), false));

        // Ulamog, the Defiler enters the battlefield with a number of +1/+1 counters on it equal to the greatest mana value among cards in exile.
        this.addAbility(
                new EntersBattlefieldAbility(
                        new AddCountersSourceEffect(
                                CounterType.P1P1.createInstance(), UlamogTheDefilerValue.instance, false
                        ), "with a number of +1/+1 counters on it equal to " +
                        "the greatest mana value among cards in exile"
                ).addHint(UlamogTheDefilerValue.hint)
        );

        // Ulamog has annihilator X, where X is the number of +1/+1 counters on it.
        this.addAbility(new SimpleStaticAbility(new UlamogTheDefilerContinuousAbility()));
    }

    private UlamogTheDefiler(final UlamogTheDefiler card) {
        super(card);
    }

    @Override
    public UlamogTheDefiler copy() {
        return new UlamogTheDefiler(this);
    }
}

class UlamogTheDefilerTargetEffect extends OneShotEffect {

    UlamogTheDefilerTargetEffect() {
        super(Outcome.Detriment);
        staticText = "target opponent exiles half their library, rounded up";
    }

    private UlamogTheDefilerTargetEffect(final UlamogTheDefilerTargetEffect effect) {
        super(effect);
    }

    @Override
    public UlamogTheDefilerTargetEffect copy() {
        return new UlamogTheDefilerTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (opponent == null) {
            return false;
        }
        int toExile = (opponent.getLibrary().size() + 1) / 2;
        Set<Card> cards = opponent.getLibrary().getTopCards(game, toExile);
        if (cards.isEmpty()) {
            return false;
        }
        opponent.moveCardsToExile(cards, source, game, true, null, "");
        return true;
    }

}

enum UlamogTheDefilerValue implements DynamicValue {
    instance;

    static final Hint hint = new ValueHint("Greatest mana value among cards in exile", instance);

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getExile()
                .getAllCardsByRange(game, sourceAbility.getControllerId())
                .stream()
                .mapToInt(Card::getManaValue)
                .max()
                .orElse(0);
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class UlamogTheDefilerContinuousAbility extends ContinuousEffectImpl {

    // Keep the last annihilator ability added.
    private Ability ability;
    // Keep the last annihilator amount added.
    private int lastAmount;

    UlamogTheDefilerContinuousAbility() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} has annihilator X, where X is the number of +1/+1 counters on it";
        this.addDependencyType(DependencyType.AddingAbility);
        this.ability = new AnnihilatorAbility(0);
        this.lastAmount = 0;
    }

    private UlamogTheDefilerContinuousAbility(final UlamogTheDefilerContinuousAbility effect) {
        super(effect);
        this.ability = effect.ability.copy();
        // From GainAbilitySourceEffect:
        ability.newId(); // This is needed if the effect is copied e.g. by a clone so the ability can be added multiple times to permanents
        this.lastAmount = effect.lastAmount;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (getAffectedObjectsSet()) {
            Permanent permanent = game.getPermanentEntering(source.getSourceId());
            if (permanent != null) {
                affectedObjectList.add(new MageObjectReference(source.getSourceId(), game.getState().getZoneChangeCounter(source.getSourceId()) + 1, game));
            }
        }
    }

    @Override
    public UlamogTheDefilerContinuousAbility copy() {
        return new UlamogTheDefilerContinuousAbility(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent;
        if (getAffectedObjectsSet()) {
            permanent = affectedObjectList.get(0).getPermanent(game);
        } else {
            permanent = game.getPermanent(source.getSourceId());
        }
        if (permanent != null) {
            int amount = permanent.getCounters(game).getCount(CounterType.P1P1);
            if (amount != lastAmount) {
                // Only instantiate a new ability if the number of P1P1 counters changed.
                ability = new AnnihilatorAbility(amount);
                lastAmount = amount;
            }
            permanent.addAbility(ability, source.getSourceId(), game);
        }
        return true;
    }

}

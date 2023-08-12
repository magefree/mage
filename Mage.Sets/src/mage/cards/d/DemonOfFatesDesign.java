package mage.cards.d;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.DynamicCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SacrificeCostManaValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.ConditionPermanentHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class DemonOfFatesDesign extends CardImpl {

    public DemonOfFatesDesign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Once during each of your turns, you may cast an enchantment spell by paying life equal to its mana value rather than paying its mana cost.
        this.addAbility(
                new SimpleStaticAbility(new DemonOfFatesDesignCastEffect())
                        .addHint(new ConditionPermanentHint(
                                DemonOfFatesDesignCondition.instance,
                                "Can cast with alternative cost this turn.",
                                null,
                                "Cannot cast with alternative cost this turn.",
                                null,
                                true
                        )),
                new DemonOfFatesDesignWatcher()
        );

        // {2}{B}, Sacrifice another enchantment: Demon of Fate's Design gets +X/+0 until end of turn, where X is the sacrificed enchantment's mana value.
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(
                SacrificeCostManaValue.ENCHANTMENT,
                StaticValue.get(0), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new SacrificeTargetCost(
                StaticFilters.FILTER_CONTROLLED_ANOTHER_ENCHANTMENT_SHORT_TEXT
        ));
        this.addAbility(ability);
    }

    private DemonOfFatesDesign(final DemonOfFatesDesign card) {
        super(card);
    }

    @Override
    public DemonOfFatesDesign copy() {
        return new DemonOfFatesDesign(this);
    }
}

class DemonOfFatesDesignPayLifeCost extends PayLifeCost {

    private MageObjectReference mor;

    DemonOfFatesDesignPayLifeCost(int amount) {
        super(amount);
    }

    private DemonOfFatesDesignPayLifeCost(final DemonOfFatesDesignPayLifeCost cost) {
        super(cost);
        this.mor = cost.mor;
    }

    @Override
    public DemonOfFatesDesignPayLifeCost copy() {
        return new DemonOfFatesDesignPayLifeCost(this);
    }

    MageObjectReference getMor() {
        return this.mor;
    }

    void setMor(MageObjectReference mor) {
        this.mor = mor;
    }

}

enum DemonOfFatesDesignCost implements DynamicCost {
    instance;

    @Override
    public Cost getCost(Ability ability, Game game) {
        return new DemonOfFatesDesignPayLifeCost(ability.getManaCosts().manaValue());
    }

    @Override
    public String getText(Ability ability, Game game) {
        return "Pay " + ability.getManaCosts().manaValue() + " life rather than "
                + ability.getManaCosts().getText() + '?';
    }
}

class DemonOfFatesDesignAlternativeCostSourceAbility extends AlternativeCostSourceAbility {

    private MageObjectReference mor;

    DemonOfFatesDesignAlternativeCostSourceAbility() {
        super(
                new CompoundCondition(SourceIsSpellCondition.instance, IsBeingCastFromHandCondition.instance),
                null, StaticFilters.FILTER_CARD_ENCHANTMENT,
                true, DemonOfFatesDesignCost.instance
        );
    }

    private DemonOfFatesDesignAlternativeCostSourceAbility(final DemonOfFatesDesignAlternativeCostSourceAbility effect) {
        super(effect);
    }

    @Override
    public DemonOfFatesDesignAlternativeCostSourceAbility copy() {
        return new DemonOfFatesDesignAlternativeCostSourceAbility(this);
    }

    @Override
    protected void doActivate(Game game, Ability ability) {
        super.doActivate(game, ability);
        for (Cost cost : ability.getCosts()) {
            if (cost != null && cost instanceof DemonOfFatesDesignPayLifeCost) {
                ((DemonOfFatesDesignPayLifeCost) cost).setMor(getMor(game));
            }
        }
    }

    MageObjectReference getMor(Game game) {
        return new MageObjectReference(getSourceObject(game), game);
    }
}

class DemonOfFatesDesignCastEffect extends ContinuousEffectImpl {

    private final DemonOfFatesDesignAlternativeCostSourceAbility alternativeCastingCostAbility
            = new DemonOfFatesDesignAlternativeCostSourceAbility();

    DemonOfFatesDesignCastEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Neutral);
        this.staticText = "once during each of your turns, you may cast an enchantment spell by paying life "
                + "equal to its mana value rather than paying its mana cost.";
    }

    private DemonOfFatesDesignCastEffect(final DemonOfFatesDesignCastEffect effect) {
        super(effect);
    }

    @Override
    public DemonOfFatesDesignCastEffect copy() {
        return new DemonOfFatesDesignCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        DemonOfFatesDesignWatcher watcher = game.getState().getWatcher(DemonOfFatesDesignWatcher.class);
        if (controller == null || watcher == null) {
            return false;
        }

        alternativeCastingCostAbility.setSourceId(source.getSourceId());
        if (!watcher.canAbilityBeUsed(game, source, alternativeCastingCostAbility.getMor(game))) {
            return false;
        }

        controller.getAlternativeSourceCosts().add(alternativeCastingCostAbility);
        return true;
    }
}

class DemonOfFatesDesignWatcher extends Watcher {

    private final Set<MageObjectReference> usedFrom = new HashSet<>();

    public DemonOfFatesDesignWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getSpell(event.getTargetId());
            if (spell != null) {
                for (Cost cost : spell.getStackAbility().getCosts()) {
                    if (cost != null && cost instanceof DemonOfFatesDesignPayLifeCost) {
                        usedFrom.add(((DemonOfFatesDesignPayLifeCost) cost).getMor());
                    }
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedFrom.clear();
    }

    public boolean canAbilityBeUsed(Game game, Ability source, MageObjectReference mor) {
        return game.isActivePlayer(source.getControllerId()) && !usedFrom.contains(mor);
    }
}


enum DemonOfFatesDesignCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        DemonOfFatesDesignWatcher watcher = game.getState().getWatcher(DemonOfFatesDesignWatcher.class);
        return watcher != null
                && watcher.canAbilityBeUsed(game, source, new MageObjectReference(source.getSourceId(), game));
    }
}
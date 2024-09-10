package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceEnteredThisTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.TrampleOverPlaneswalkersAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author Fubs
 */
public final class ThrastaTempestsRoar extends CardImpl {

    private static final ValueHint hint = new ValueHint("Spells cast this turn", ThrastaDynamicValue.instance);

    public ThrastaTempestsRoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{10}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // This spell costs 3 less to cast for each other spell cast this turn
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(3, ThrastaDynamicValue.instance)
        ).addHint(hint));

        // Trample, Haste, and Trample over planeswalkers
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(TrampleOverPlaneswalkersAbility.getInstance());

        // Thrasta has hexproof as long as it entered the battlefield this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield),
                SourceEnteredThisTurnCondition.instance, "{this} has hexproof as long as it entered the battlefield this turn"
        )));
    }

    private ThrastaTempestsRoar(final ThrastaTempestsRoar card) {
        super(card);
    }

    @Override
    public ThrastaTempestsRoar copy() {
        return new ThrastaTempestsRoar(this);
    }
}

enum ThrastaDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher != null) {
            return watcher.getAmountOfSpellsAllPlayersCastOnCurrentTurn();
        }
        return 0;
    }

    @Override
    public ThrastaDynamicValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "other spell cast this turn";
    }
}

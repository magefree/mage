package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPlayer;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FirstTargetPointer;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author Grath
 */
public final class TheLordOfPain extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("their first spell each turn");

    static {
        filter.add(TheLordOfPainPredicate.instance);
    }

    public TheLordOfPain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Your opponents can't gain life.
        this.addAbility(new SimpleStaticAbility(
                new CantGainLifeAllEffect(Duration.WhileOnBattlefield, TargetController.OPPONENT)
        ));

        // Whenever a player casts their first spell each turn, choose another target player. The Lord of Pain deals damage equal to that spell's mana value to the chosen player.
        Ability ability = new SpellCastAllTriggeredAbility(
                new DamageTargetEffect(TheLordOfPainValue.instance)
                        .setText("choose another target player. {this} deals damage equal to that spell's mana value to the chosen player")
                , filter, false, SetTargetPointer.PLAYER);
        ability.setTargetAdjuster(TheLordOfPainTargetAdjuster.instance);
        this.addAbility(ability);
    }

    private TheLordOfPain(final TheLordOfPain card) {
        super(card);
    }

    @Override
    public TheLordOfPain copy() {
        return new TheLordOfPain(this);
    }
}

enum TheLordOfPainPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return game.getState()
                .getWatcher(SpellsCastWatcher.class)
                .getCount(input.getControllerId()) == 1;
    }
}

enum TheLordOfPainTargetAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        UUID opponentId = ability.getEffects().get(0).getTargetPointer().getFirst(game, ability);
        ability.getTargets().clear();
        ability.getAllEffects().setTargetPointer(new FirstTargetPointer());
        FilterPlayer filter = new FilterPlayer("another target player");
        filter.add(Predicates.not(new PlayerIdPredicate(opponentId)));
        Target newTarget = new TargetPlayer(filter);
        ability.addTarget(newTarget);
    }
}

enum TheLordOfPainValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Object spell = effect.getValue("spellCast");
        if (spell instanceof Spell) {
            return ((Spell) spell).getManaValue();
        }
        return 0;
    }

    @Override
    public TheLordOfPainValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "that spell's mana value";
    }
}

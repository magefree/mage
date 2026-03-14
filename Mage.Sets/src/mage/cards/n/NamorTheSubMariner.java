package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.MerfolkToken;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NamorTheSubMariner extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.MERFOLK));
    private static final Hint hint = new ValueHint("Merfolk you control", xValue);
    private static final FilterSpell filter = new FilterSpell("a noncreature spell with one or more blue mana symbols in its mana cost");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(NamorTheSubMarinerPredicate.instance);
    }

    public NamorTheSubMariner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Namor's power is equal to the number of Merfolk you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(xValue)));

        // Whenever you cast a noncreature spell with one or more blue mana symbols in its mana cost, create that many 1/1 blue Merfolk creature tokens.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new MerfolkToken(), NamorTheSubMarinerValue.instance), filter, false
        ));
    }

    private NamorTheSubMariner(final NamorTheSubMariner card) {
        super(card);
    }

    @Override
    public NamorTheSubMariner copy() {
        return new NamorTheSubMariner(this);
    }
}

enum NamorTheSubMarinerPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return input
                .getManaCost()
                .stream()
                .anyMatch(manaCost -> manaCost.containsColor(ColoredManaSymbol.U));
    }
}

enum NamorTheSubMarinerValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Spell spell = (Spell) effect.getValue("spellCast");
        return spell != null
                ? spell
                .getManaCost()
                .stream()
                .mapToInt(manaCost -> manaCost.containsColor(ColoredManaSymbol.U) ? 1 : 0)
                .sum()
                : 0;
    }

    @Override
    public NamorTheSubMarinerValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "that many";
    }

    @Override
    public String toString() {
        return "1";
    }
}

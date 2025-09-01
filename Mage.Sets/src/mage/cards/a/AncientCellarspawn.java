package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.common.TargetOpponent;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AncientCellarspawn extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("each spell you cast that's a Demon, Horror, or Nightmare");
    private static final FilterSpell filter2
            = new FilterSpell("a spell, if the amount of mana spent to cast it was less than its mana value");

    static {
        filter.add(Predicates.or(
                SubType.DEMON.getPredicate(),
                SubType.HORROR.getPredicate(),
                SubType.NIGHTMARE.getPredicate()
        ));
        filter2.add(AncientCellarspawnPredicate.instance);
    }

    public AncientCellarspawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Each spell you cast that's a Demon, Horror, or Nightmare costs {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever you cast a spell, if the amount of mana spent to cast it was less than its mana value, target opponent loses life equal to the difference.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new LoseLifeTargetEffect(AncientCellarspawnValue.instance)
                        .setText("target opponent loses life equal to the difference"),
                filter2, false
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private AncientCellarspawn(final AncientCellarspawn card) {
        super(card);
    }

    @Override
    public AncientCellarspawn copy() {
        return new AncientCellarspawn(this);
    }
}

enum AncientCellarspawnPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return ManaPaidSourceWatcher.getTotalPaid(input.getSourceId(), game) < input.getManaValue();
    }
}

enum AncientCellarspawnValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable(effect.getValue("spellCast"))
                .filter(Spell.class::isInstance)
                .map(Spell.class::cast)
                .map(spell -> Math.abs(spell.getManaValue() - ManaPaidSourceWatcher.getTotalPaid(spell.getId(), game)))
                .orElse(0);
    }

    @Override
    public AncientCellarspawnValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}

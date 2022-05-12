package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Loki
 */
public final class RageExtractor extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell with {P} in its mana cost");

    static {
        filter.add(RageExtractorPredicate.instance);
    }

    public RageExtractor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{R/P}");

        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamageTargetEffect(RageExtractorValue.instance)
                        .setText("{this} deals damage equal to that spell's mana value to any target"),
                filter, false
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private RageExtractor(final RageExtractor card) {
        super(card);
    }

    @Override
    public RageExtractor copy() {
        return new RageExtractor(this);
    }
}

enum RageExtractorPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return ((Spell) input).getCard().getManaCost().stream().anyMatch(ManaCost::isPhyrexian);
    }
}

enum RageExtractorValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Spell spell = (Spell) effect.getValue("spellCast");
        return spell != null ? spell.getManaValue() : 0;
    }

    @Override
    public RageExtractorValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

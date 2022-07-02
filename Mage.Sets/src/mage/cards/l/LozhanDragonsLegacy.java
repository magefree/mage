package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePlayerOrPlaneswalker;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.common.TargetAnyTarget;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LozhanDragonsLegacy extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an Adventure spell or Dragon spell");
    private static final FilterCreaturePlayerOrPlaneswalker filter2
            = new FilterCreaturePlayerOrPlaneswalker("any target that isn't a commander");

    static {
        filter.add(Predicates.or(
                SubType.ADVENTURE.getPredicate(),
                SubType.DRAGON.getPredicate()
        ));
        filter2.getPermanentFilter().add(Predicates.not(CommanderPredicate.instance));
    }

    public LozhanDragonsLegacy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast an Adventure spell or Dragon spell, Lozhan, Dragons' Legacy deals damage equal to that spell's mana value to any target that isn't a commander.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamageTargetEffect(LozhanDragonsLegacyValue.instance), filter, false
        );
        ability.addTarget(new TargetAnyTarget(filter2));
        this.addAbility(ability);
    }

    private LozhanDragonsLegacy(final LozhanDragonsLegacy card) {
        super(card);
    }

    @Override
    public LozhanDragonsLegacy copy() {
        return new LozhanDragonsLegacy(this);
    }
}

enum LozhanDragonsLegacyValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional.ofNullable(effect.getValue("spellCast"))
                .map(Spell.class::cast)
                .map(Spell::getManaValue)
                .orElse(0);
    }

    @Override
    public LozhanDragonsLegacyValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "that spells' mana value";
    }

    @Override
    public String toString() {
        return "";
    }
}

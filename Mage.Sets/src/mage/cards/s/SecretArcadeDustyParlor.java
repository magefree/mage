package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SecretArcadeDustyParlor extends RoomCard {

    public SecretArcadeDustyParlor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, "{4}{W}", "{2}{W}");

        // Secret Arcade
        // Nonland permanents you control and permanent spells you control are enchantments in addition to their other types.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new SecretArcadeEffect()));

        // Dusty Parlor
        // Whenever you cast an enchantment spell, put a number of +1/+1 counters equal to that spell's mana value on up to one target creature.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(), DustyParlorValue.instance)
                        .setText("put a number of +1/+1 counters equal to that spell's mana value on up to one target creature"),
                StaticFilters.FILTER_SPELL_AN_ENCHANTMENT, false
        );
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.getRightHalfCard().addAbility(ability);
    }

    private SecretArcadeDustyParlor(final SecretArcadeDustyParlor card) {
        super(card);
    }

    @Override
    public SecretArcadeDustyParlor copy() {
        return new SecretArcadeDustyParlor(this);
    }
}

class SecretArcadeEffect extends ContinuousEffectImpl {

    SecretArcadeEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.dependencyTypes.add(DependencyType.EnchantmentAddingRemoving);
        this.dependencyTypes.add(DependencyType.AuraAddingRemoving);
        staticText = "nonland permanents you control and permanent spells you control " +
                "are enchantments in addition to their other types";
    }

    private SecretArcadeEffect(final SecretArcadeEffect effect) {
        super(effect);
    }

    @Override
    public SecretArcadeEffect copy() {
        return new SecretArcadeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND, source.getControllerId(), source, game
        )) {
            permanent.addCardType(game, CardType.ENCHANTMENT);
        }
        for (StackObject stackObject : game.getStack()) {
            if (stackObject instanceof Spell
                    && stackObject.isPermanent(game)
                    && stackObject.isControlledBy(source.getControllerId())) {
                stackObject.addCardType(game, CardType.ENCHANTMENT);
            }
        }
        return true;
    }
}

enum DustyParlorValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable(effect.getValue("spellCast"))
                .map(Spell.class::cast)
                .map(Spell::getManaValue)
                .orElse(0);
    }

    @Override
    public DustyParlorValue copy() {
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

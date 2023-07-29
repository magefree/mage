
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayAllEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class SphereOfSafety extends CardImpl {

    public SphereOfSafety(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{W}");

        // Creatures can't attack you or a planeswalker you control unless their controller pays {X} for each of those creatures, where X is the number of enchantments you control.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new SphereOfSafetyPayManaToAttackAllEffect());
        ability.addHint(new ValueHint(
                NumberOfEnchantmentsYouControl.instance.getMessage(),
                NumberOfEnchantmentsYouControl.instance)
        );
        this.addAbility(ability);
    }

    private SphereOfSafety(final SphereOfSafety card) {
        super(card);
    }

    @Override
    public SphereOfSafety copy() {
        return new SphereOfSafety(this);
    }

}

class SphereOfSafetyPayManaToAttackAllEffect extends CantAttackYouUnlessPayAllEffect {

    SphereOfSafetyPayManaToAttackAllEffect() {
        super(Duration.WhileOnBattlefield, new ManaCostsImpl<>("{X}"), Scope.YOU_AND_CONTROLLED_PLANESWALKERS);
        staticText = "Creatures can't attack you or planeswalkers you control unless their controller pays {X} for each of those creatures, where X is the number of enchantments you control.";
    }

    SphereOfSafetyPayManaToAttackAllEffect(SphereOfSafetyPayManaToAttackAllEffect effect) {
        super(effect);
    }

    @Override
    public ManaCosts getManaCostToPay(GameEvent event, Ability source, Game game) {
        int enchantments = NumberOfEnchantmentsYouControl.instance.calculate(game, source, this);
        if (enchantments > 0) {
            return new ManaCostsImpl<>("{" + enchantments + '}');
        }
        return null;
    }

    @Override
    public SphereOfSafetyPayManaToAttackAllEffect copy() {
        return new SphereOfSafetyPayManaToAttackAllEffect(this);
    }

}

enum NumberOfEnchantmentsYouControl implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_ENCHANTMENT, sourceAbility.getControllerId(), game);
    }

    @Override
    public NumberOfEnchantmentsYouControl copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "Number of enchantments controlled by controller";
    }
}
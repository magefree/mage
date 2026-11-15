package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderAllEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessControlledEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessGreaterThanPowerPredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class GhaltaTheImmovable extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature you control with toughness greater than its power");

    static {
        filter.add(ToughnessGreaterThanPowerPredicate.instance);
    }

    public GhaltaTheImmovable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{8}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(7);

        // This spell costs {X} less to cast, where X is the greatest toughness among creatures you control.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL, new SpellCostReductionSourceEffect(GreatestAmongPermanentsValue.TOUGHNESS_CONTROLLED_CREATURES)
        ).setRuleAtTheTop(true).addHint(GreatestAmongPermanentsValue.TOUGHNESS_CONTROLLED_CREATURES.getHint()));

        // Creatures you control can attack as though they didn't have defender.
        this.addAbility(new SimpleStaticAbility(new CanAttackAsThoughItDidntHaveDefenderAllEffect(
            Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURES
        )));

        // Each creature you control with toughness greater than its power assigns combat damage equal to its toughness rather than its power.
        this.addAbility(new SimpleStaticAbility(new CombatDamageByToughnessControlledEffect(filter)));
    }

    private GhaltaTheImmovable(final GhaltaTheImmovable card) {
        super(card);
    }

    @Override
    public GhaltaTheImmovable copy() {
        return new GhaltaTheImmovable(this);
    }
}

package mage.cards.g;

import mage.abilities.TriggeredAbility;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class GimlisRecklessMight extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterAttackingCreature("attacking creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public GimlisRecklessMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // Creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES)));
        // Formidable -- Whenever you attack, if creatures you control have total power 8 or greater, target attacking creature you control fights up to one target creature you don't control.
        TriggeredAbility ability = new AttacksWithCreaturesTriggeredAbility(new FightTargetsEffect(),1);
        ability.addTarget(new TargetPermanent(filter));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        ability.setAbilityWord(AbilityWord.FORMIDABLE);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, FormidableCondition.instance,
                "<i>Formidable</i> &mdash; Whenever you attack, if creatures you control have total power 8 or greater, target attacking creature you control fights up to one target creature you don't control."));
    }

    private GimlisRecklessMight(final GimlisRecklessMight card) {
        super(card);
    }

    @Override
    public GimlisRecklessMight copy() {
        return new GimlisRecklessMight(this);
    }
}

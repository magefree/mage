package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class GimlisRecklessMight extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("attacking creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public GimlisRecklessMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // Creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES
        )));

        // Formidable -- Whenever you attack, if creatures you control have total power 8 or greater, target attacking creature you control fights up to one target creature you don't control.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new FightTargetsEffect(), 1)
                .withInterveningIf(FormidableCondition.instance);
        ability.addTarget(new TargetPermanent(filter));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability.setAbilityWord(AbilityWord.FORMIDABLE));
    }

    private GimlisRecklessMight(final GimlisRecklessMight card) {
        super(card);
    }

    @Override
    public GimlisRecklessMight copy() {
        return new GimlisRecklessMight(this);
    }
}

package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BecomesSubtypeAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DauntAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.PoisonousAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class HishOfTheSnakeCult extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.SERPENT, "Serpents you control");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent(SubType.SNAKE, "Snakes you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public HishOfTheSnakeCult(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Serpents you control are Snakes.
        this.addAbility(new SimpleStaticAbility(new BecomesSubtypeAllEffect(
                Duration.WhileOnBattlefield, Arrays.asList(SubType.SNAKE), filter, true
        ).setText("Serpents you control are Snakes.")));

        // Snakes you control have daunt, deathtouch, and poisonous 2.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                new DauntAbility(true), Duration.WhileOnBattlefield, filter2
        ).setText("Snakes you control have daunt"));
        ability.addEffect(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, filter2
        ).concatBy(", ").setText("deathtouch"));
        ability.addEffect(new GainAbilityControlledEffect(
                new PoisonousAbility(2), Duration.WhileOnBattlefield, filter2
        ).concatBy(", and").setText("poisonous 2. " +
                "<i>(A creature with daunt can't be blocked by creatures with power 2 or less. Whenever a creature with poisonous 2 deals combat damage to a player, that player gets two poison counters.)</i>"));
        this.addAbility(ability);
    }

    private HishOfTheSnakeCult(final HishOfTheSnakeCult card) {
        super(card);
    }

    @Override
    public HishOfTheSnakeCult copy() {
        return new HishOfTheSnakeCult(this);
    }
}

package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostModificationThatTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CallapheBelovedOfTheSea extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("creatures and enchantments");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public CallapheBelovedOfTheSea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMIGOD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Callaphe's power is equal to your to devotion to blue.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerSourceEffect(DevotionCount.U, Duration.EndOfGame)
                .setText("{this}'s power is equal to your devotion to blue")
        ).addHint(DevotionCount.U.getHint()));

        // Creatures and enchantments you control have "Spells your opponents cast that target this permanent cost {1} more to cast".
        Ability gainAbility = new SimpleStaticAbility(Zone.BATTLEFIELD,
                new SpellsCostModificationThatTargetSourceEffect(1, new FilterCard("Spells"), TargetController.OPPONENT)
                        .withTargetName("this permanent")
        );
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledEffect(gainAbility, Duration.WhileOnBattlefield, filter).withForceQuotes()
        ));
    }

    private CallapheBelovedOfTheSea(final CallapheBelovedOfTheSea card) {
        super(card);
    }

    @Override
    public CallapheBelovedOfTheSea copy() {
        return new CallapheBelovedOfTheSea(this);
    }
}

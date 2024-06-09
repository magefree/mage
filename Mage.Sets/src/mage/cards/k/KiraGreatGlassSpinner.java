package mage.cards.k;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BecomesTargetSourceFirstTimeTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class KiraGreatGlassSpinner extends CardImpl {

    public KiraGreatGlassSpinner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.SPIRIT);
        this.supertype.add(SuperType.LEGENDARY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Creatures you control have "Whenever this creature becomes the target of a spell or ability for the first time each turn, counter that spell or ability."
        TriggeredAbility gainedAbility = new BecomesTargetSourceFirstTimeTriggeredAbility(
                new CounterTargetEffect().setText("counter that spell or ability"),
                StaticFilters.FILTER_SPELL_OR_ABILITY_A, SetTargetPointer.SPELL, false
        ).setTriggerPhrase("Whenever this creature becomes the target of a spell or ability for the first time each turn, ");
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                gainedAbility, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES)));

    }

    private KiraGreatGlassSpinner(final KiraGreatGlassSpinner card) {
        super(card);
    }

    @Override
    public KiraGreatGlassSpinner copy() {
        return new KiraGreatGlassSpinner(this);
    }
}

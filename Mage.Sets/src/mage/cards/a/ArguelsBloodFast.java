
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesToughness;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArguelsBloodFast extends TransformingDoubleFacedCard {

    public ArguelsBloodFast(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{1}{B}",
                "Temple of Aclazotz",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // {1}{B}, Pay 2 life: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new PayLifeCost(2));
        this.getLeftHalfCard().addAbility(ability);

        // At the beginning of your upkeep, if you have 5 or less life, you may transform Arguel's Blood Fast.
        this.getLeftHalfCard().addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(), TargetController.YOU, true),
                FatefulHourCondition.instance,
                "At the beginning of your upkeep, if you have 5 or less life, you may transform {this}"
        ));

        // Temple of Aclazotz
        // Transforms from Arguel's Blood Fast.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new InfoEffect("<i>(Transforms from Arguel's Blood Fast.)</i>")));

        // {T}: Add {B}
        this.getRightHalfCard().addAbility(new BlackManaAbility());

        // {T}, Sacrifice a creature: You gain life equal to the sacrificed creature's toughness.
        ability = new SimpleActivatedAbility(new GainLifeEffect(SacrificeCostCreaturesToughness.instance)
                .setText("you gain life equal to the sacrificed creature's toughness"), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.getRightHalfCard().addAbility(ability);
    }

    private ArguelsBloodFast(final ArguelsBloodFast card) {
        super(card);
    }

    @Override
    public ArguelsBloodFast copy() {
        return new ArguelsBloodFast(this);
    }
}

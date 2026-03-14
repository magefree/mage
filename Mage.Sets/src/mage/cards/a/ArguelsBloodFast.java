package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesToughness;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArguelsBloodFast extends TransformingDoubleFacedCard {

    public ArguelsBloodFast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{1}{B}",
                "Temple of Aclazotz",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.LAND}, new SubType[]{},"");

        // {1}{B}, Pay 2 life: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new PayLifeCost(2));
        this.getLeftHalfCard().addAbility(ability);

        // At the beginning of your upkeep, if you have 5 or less life, you may transform Arguel's Blood Fast.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(), true
        ).withInterveningIf(FatefulHourCondition.instance));

        // Temple of Aclazotz
        // {T}: Add {B}
        this.getRightHalfCard().addAbility(new BlackManaAbility());

        // {T}, Sacrifice a creature: You gain life equal to the sacrificed creature's toughness.
        Ability activatedAbility = new SimpleActivatedAbility(new GainLifeEffect(SacrificeCostCreaturesToughness.instance)
                .setText("you gain life equal to the sacrificed creature's toughness"), new TapSourceCost());
        activatedAbility.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));
        this.getRightHalfCard().addAbility(activatedAbility);
    }

    private ArguelsBloodFast(final ArguelsBloodFast card) {
        super(card);
    }

    @Override
    public ArguelsBloodFast copy() {
        return new ArguelsBloodFast(this);
    }
}

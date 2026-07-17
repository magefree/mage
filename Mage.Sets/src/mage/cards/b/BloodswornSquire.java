package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class BloodswornSquire extends TransformingDoubleFacedCard {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURES);

    public BloodswornSquire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE, SubType.SOLDIER}, "{3}{B}",
                "Bloodsworn Knight",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE, SubType.KNIGHT}, "B");

        // Bloodsworn Squire
        this.getLeftHalfCard().setPT(3, 3);

        // {1}{B}, Discard a card: Bloodsworn Squire gains indestructible until end of turn. Tap it. Then if there are four or more creature cards in your graveyard, transform Bloodsworn Squire.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new DiscardCardCost());
        ability.addEffect(new TapSourceEffect().setText("tap it"));
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(),
                new CardsInControllerGraveyardCondition(4, StaticFilters.FILTER_CARD_CREATURES),
                "Then if there are four or more creature cards in your graveyard, transform {this}"
        ));
        this.getLeftHalfCard().addAbility(ability);

        // Bloodsworn Knight
        this.getRightHalfCard().setPT(0, 0);

        // Bloodsworn Knight's power and toughness are each equal to the number of creature cards in your graveyard.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(xValue)));

        // {1}{B}, Discard a card: Bloodsworn Knight gains indestructible until end of turn. Tap it.
        ability = new SimpleActivatedAbility(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new DiscardCardCost());
        ability.addEffect(new TapSourceEffect().setText("tap it"));
        this.getRightHalfCard().addAbility(ability);
    }

    private BloodswornSquire(final BloodswornSquire card) {
        super(card);
    }

    @Override
    public BloodswornSquire copy() {
        return new BloodswornSquire(this);
    }
}

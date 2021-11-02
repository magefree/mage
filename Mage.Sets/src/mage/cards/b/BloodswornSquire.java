package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TransformAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author weirddan455
 */
public final class BloodswornSquire extends CardImpl {

    public BloodswornSquire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.transformable = true;
        this.secondSideCardClazz = mage.cards.b.BloodswornKnight.class;

        this.addAbility(new TransformAbility());

        // {1}{B}, Discard a card: Bloodsworn Squire gains indestructible until end of turn. Tap it. Then if there are four or more creature cards in your graveyard, transform Bloodsworn Squire.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new DiscardCardCost());
        ability.addEffect(new TapSourceEffect().setText("tap it"));
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(true, true),
                new CardsInControllerGraveyardCondition(4, StaticFilters.FILTER_CARD_CREATURES),
                "Then if there are four or more creature cards in your graveyard, transform {this}"
        ));
        this.addAbility(ability);
    }

    private BloodswornSquire(final BloodswornSquire card) {
        super(card);
    }

    @Override
    public BloodswornSquire copy() {
        return new BloodswornSquire(this);
    }
}

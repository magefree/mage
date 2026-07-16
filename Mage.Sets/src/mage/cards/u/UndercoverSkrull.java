package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.IsAllCreatureTypesSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class UndercoverSkrull extends CardImpl {

    public UndercoverSkrull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SKRULL);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // As long as there are two or more creature cards in your graveyard, this creature gets +2/+2 and is all creature types.
        Ability ability = new SimpleStaticAbility(
            Zone.BATTLEFIELD,
            new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
                new CardsInControllerGraveyardCondition(2, StaticFilters.FILTER_CARD_CREATURE),
                "As long as there are two or more creature cards in your graveyard, {this} gets +2/+2"));
        ability.addEffect(
            new ConditionalContinuousEffect(
                new IsAllCreatureTypesSourceEffect(),
                new CardsInControllerGraveyardCondition(2, StaticFilters.FILTER_CARD_CREATURE),
                "and is all creature types."));
        this.addAbility(ability);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private UndercoverSkrull(final UndercoverSkrull card) {
        super(card);
    }

    @Override
    public UndercoverSkrull copy() {
        return new UndercoverSkrull(this);
    }
}

package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class WaterloggedHulk extends TransformingDoubleFacedCard {

    public WaterloggedHulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{U}",
                "Watertight Gondola",
                new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.VEHICLE}, "U"
        );

        // Waterlogged Hulk
        // {T}: Mill a card.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(
                new MillCardsControllerEffect(1),
                new TapSourceCost()
        ));

        // Craft with Island {3}{U}
        this.getLeftHalfCard().addAbility(new CraftAbility(
                "{3}{U}", "Island",
                "another Island you control or an Island card from your graveyard",
                SubType.ISLAND.getPredicate()
        ));

        // Watertight Gondola
        this.getRightHalfCard().setPT(4, 4);

        // Vigilance
        this.getRightHalfCard().addAbility(VigilanceAbility.getInstance());

        // Descend 8 -- Watertight Gondola can't be blocked as long as there are eight or more permanent cards in your graveyard.
        Ability ability = new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(),
                DescendCondition.EIGHT
        ).setText("{this} can't be blocked as long as there are eight or more permanent cards in your graveyard"));
        this.getRightHalfCard().addAbility(ability.addHint(DescendCondition.getHint()).setAbilityWord(AbilityWord.DESCEND_8));

        // Crew 1
        this.getRightHalfCard().addAbility(new CrewAbility(1));
    }

    private WaterloggedHulk(final WaterloggedHulk card) {
        super(card);
    }

    @Override
    public WaterloggedHulk copy() {
        return new WaterloggedHulk(this);
    }
}

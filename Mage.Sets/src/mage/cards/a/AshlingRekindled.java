package mage.cards.a;

import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.common.TransformsOrEntersTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.mana.AddConditionalManaOfAnyColorEffect;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.abilities.meta.OrTriggeredAbility;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AshlingRekindled extends TransformingDoubleFacedCard {

    private static final FilterSpell filter = new FilterSpell("spells with mana value 4 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 3));
    }

    public AshlingRekindled(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELEMENTAL, SubType.SORCERER}, "{1}{R}",
                "Ashling, Rimebound",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELEMENTAL, SubType.WIZARD}, "U"
        );
        this.getLeftHalfCard().setPT(1, 3);
        this.getRightHalfCard().setPT(1, 3);

        // Whenever this creature enters or transforms into Ashling, Rekindled, you may discard a card. If you do, draw a card.
        this.getLeftHalfCard().addAbility(new TransformsOrEntersTriggeredAbility(
                new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost()), false
        ));

        // At the beginning of your first main phase, you may pay {U}. If you do, transform Ashling.
        this.getLeftHalfCard().addAbility(new BeginningOfFirstMainTriggeredAbility(
                new DoIfCostPaid(new TransformSourceEffect(), new ManaCostsImpl<>("{U}"))
        ));

        // Ashling, Rimebound
        // Whenever this creature transforms into Ashling, Rimebound and at the beginning of your first main phase, add two mana of any one color. Spend this mana only to cast spells with mana value 4 or greater.
        this.getRightHalfCard().addAbility(new OrTriggeredAbility(
                Zone.BATTLEFIELD,
                new AddConditionalManaOfAnyColorEffect(2, new ConditionalSpellManaBuilder(filter)),
                false,
                "Whenever this creature transforms into {this} and at the beginning of your first main phase, ",
                new TransformIntoSourceTriggeredAbility(null),
                new BeginningOfFirstMainTriggeredAbility(null)
        ));

        // At the beginning of your first main phase, you may pay {R}. If you do, transform Ashling.
        this.getRightHalfCard().addAbility(new BeginningOfFirstMainTriggeredAbility(
                new DoIfCostPaid(new TransformSourceEffect(), new ManaCostsImpl<>("{R}"))
        ));
    }

    private AshlingRekindled(final AshlingRekindled card) {
        super(card);
    }

    @Override
    public AshlingRekindled copy() {
        return new AshlingRekindled(this);
    }
}

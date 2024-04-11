package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.keyword.EmergeAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author jimga150
 */
public final class CrestingMosasaurus extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("non-Dinosaur creature");

    static {
        filter.add(Predicates.not(SubType.DINOSAUR.getPredicate()));
    }

    public CrestingMosasaurus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}{U}");
        
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(8);

        // Emerge {6}{U}
        this.addAbility(new EmergeAbility(this, "{6}{U}"));

        // When Cresting Mosasaurus enters the battlefield, if you cast it, return each non-Dinosaur creature to its owner's hand.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ReturnToHandFromBattlefieldAllEffect(filter), false),
                CastFromEverywhereSourceCondition.instance,
                "When {this} enters the battlefield, if you cast it, return each non-Dinosaur creature to its owner's hand."));
    }

    private CrestingMosasaurus(final CrestingMosasaurus card) {
        super(card);
    }

    @Override
    public CrestingMosasaurus copy() {
        return new CrestingMosasaurus(this);
    }
}


package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;

/**
 *
 * @author LevelX2
 */
public final class NantukoShaman extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent();
    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public NantukoShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Nantuko Shaman enters the battlefield, if you control no tapped lands, draw a card.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0),
                "When {this} enters the battlefield, if you control no tapped lands, draw a card.");
        this.addAbility(ability);

        // Suspend 1-{2}{G}{G}
        this.addAbility(new SuspendAbility(1, new ManaCostsImpl<>("{2}{G}{G}"), this));
    }

    private NantukoShaman(final NantukoShaman card) {
        super(card);
    }

    @Override
    public NantukoShaman copy() {
        return new NantukoShaman(this);
    }
}

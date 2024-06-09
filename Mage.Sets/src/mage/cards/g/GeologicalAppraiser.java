package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GeologicalAppraiser extends CardImpl {

    public GeologicalAppraiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Geological Appraiser enters the battlefield, if you cast it, discover 3.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DiscoverEffect(3)),
                CastFromEverywhereSourceCondition.instance,
                "When {this} enters the battlefield, if you cast it, discover 3."
        ));
    }

    private GeologicalAppraiser(final GeologicalAppraiser card) {
        super(card);
    }

    @Override
    public GeologicalAppraiser copy() {
        return new GeologicalAppraiser(this);
    }
}

package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OutcasterTrailblazer extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another creature with power 4 or greater");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public OutcasterTrailblazer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Outcaster Trailblazer enters the battlefield, add one mana of any color.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddManaOfAnyColorEffect()));

        // Whenever another creature with power 4 or greater enters the battlefield under your control, draw a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new DrawCardSourceControllerEffect(1), filter));

        // Plot {2}{G}
        this.addAbility(new PlotAbility(this, "{2}{G}"));
    }

    private OutcasterTrailblazer(final OutcasterTrailblazer card) {
        super(card);
    }

    @Override
    public OutcasterTrailblazer copy() {
        return new OutcasterTrailblazer(this);
    }
}

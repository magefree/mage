package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class StockpilingCelebrant extends CardImpl {

    private static final FilterControlledPermanent filter =
            new FilterControlledPermanent("another target nonland permanent you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public StockpilingCelebrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Stockpiling Celebrant enters the battlefield, you may return another target nonland permanent you control to its owner's hand. If you do, scry 2.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), true);
        ability.addTarget(new TargetControlledPermanent(filter));
        ability.addEffect(new ScryEffect(2, false).concatBy("If you do,"));
        this.addAbility(ability);
    }

    private StockpilingCelebrant(final StockpilingCelebrant card) {
        super(card);
    }

    @Override
    public StockpilingCelebrant copy() {
        return new StockpilingCelebrant(this);
    }
}

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RenetTemporalApprentice extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nonland permanent that entered this turn");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(EnteredThisTurnPredicate.instance);
    }


    public RenetTemporalApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Renet enters, return each other nonland permanent that entered this turn to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new ReturnToHandFromBattlefieldAllEffect(filter)
                .setText("return each other nonland permanent that entered this turn to its owner's hand"),
            false
        ));
    }

    private RenetTemporalApprentice(final RenetTemporalApprentice card) {
        super(card);
    }

    @Override
    public RenetTemporalApprentice copy() {
        return new RenetTemporalApprentice(this);
    }
}

package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SzarekhTheSilentKing extends CardImpl {

    private static final FilterCard filter = new FilterCard("an artifact creature card or Vehicle card");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                        CardType.ARTIFACT.getPredicate(),
                        CardType.CREATURE.getPredicate()
                ), SubType.VEHICLE.getPredicate()
        ));
    }

    public SzarekhTheSilentKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NECRON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // My Will Be Done -- Whenever Szarekh, the Silent King attacks, mill three cards. You may put an artifact creature card or Vehicle card from among the cards milled this way into your hand.
        this.addAbility(new AttacksTriggeredAbility(
                new MillThenPutInHandEffect(3, filter)
        ).withFlavorWord("My Will Be Done"));
    }

    private SzarekhTheSilentKing(final SzarekhTheSilentKing card) {
        super(card);
    }

    @Override
    public SzarekhTheSilentKing copy() {
        return new SzarekhTheSilentKing(this);
    }
}

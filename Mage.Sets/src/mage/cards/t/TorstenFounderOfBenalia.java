package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RevealLibraryPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.SoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TorstenFounderOfBenalia extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature and/or land cards");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public TorstenFounderOfBenalia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // When Torsten, Founder of Benalia enters the battlefield, reveal the top seven cards of your library. Put any number of creature and/or land cards from among them into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RevealLibraryPickControllerEffect(
                7, Integer.MAX_VALUE, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM, false
        )));

        // When Torsten dies, create seven 1/1 white Soldier creature tokens.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new SoldierToken(), 7)));
    }

    private TorstenFounderOfBenalia(final TorstenFounderOfBenalia card) {
        super(card);
    }

    @Override
    public TorstenFounderOfBenalia copy() {
        return new TorstenFounderOfBenalia(this);
    }
}

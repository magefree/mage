package mage.cards.l;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.WishEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 * @author TheElk801
 */
public final class LegionAngel extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Legion Angel");

    static {
        filter.add(new NamePredicate("Legion Angel"));
    }

    public LegionAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Legion Angel enters the battlefield, you may reveal a card you own named Legion Angel from outside the game and put it into your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new WishEffect(filter)
                .setText("you may reveal a card you own named Legion Angel from outside the game and put it into your hand"))
                .addHint(OpenSideboardHint.instance));
    }

    private LegionAngel(final LegionAngel card) {
        super(card);
    }

    @Override
    public LegionAngel copy() {
        return new LegionAngel(this);
    }
}

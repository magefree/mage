package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.AfterlifeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SyndicateMessenger extends CardImpl {

    public SyndicateMessenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Afterlife 1
        this.addAbility(new AfterlifeAbility(1));
    }

    private SyndicateMessenger(final SyndicateMessenger card) {
        super(card);
    }

    @Override
    public SyndicateMessenger copy() {
        return new SyndicateMessenger(this);
    }
}

package mage.cards.l;

import mage.MageInt;
import mage.abilities.keyword.DauntAbility;
import mage.abilities.keyword.EnlistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LinebreakerBaloth extends CardImpl {

    public LinebreakerBaloth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Enlist
        this.addAbility(new EnlistAbility());

        // Linebreaker Baloth can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());
    }

    private LinebreakerBaloth(final LinebreakerBaloth card) {
        super(card);
    }

    @Override
    public LinebreakerBaloth copy() {
        return new LinebreakerBaloth(this);
    }
}

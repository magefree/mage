package mage.cards.m;

import mage.MageInt;
import mage.abilities.keyword.AfterlifeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MinistrantOfObligation extends CardImpl {

    public MinistrantOfObligation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Afterlife 2
        this.addAbility(new AfterlifeAbility(2));
    }

    private MinistrantOfObligation(final MinistrantOfObligation card) {
        super(card);
    }

    @Override
    public MinistrantOfObligation copy() {
        return new MinistrantOfObligation(this);
    }
}

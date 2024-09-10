package mage.cards.r;

import java.util.UUID;

import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class ResistanceSkywarden extends CardImpl {

    public ResistanceSkywarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private ResistanceSkywarden(final ResistanceSkywarden card) {
        super(card);
    }

    @Override
    public ResistanceSkywarden copy() {
        return new ResistanceSkywarden(this);
    }
}

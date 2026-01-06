package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SunDappledCelebrant extends CardImpl {

    public SunDappledCelebrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
    }

    private SunDappledCelebrant(final SunDappledCelebrant card) {
        super(card);
    }

    @Override
    public SunDappledCelebrant copy() {
        return new SunDappledCelebrant(this);
    }
}


package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jonubuu
 */
public final class GuardianOfVituGhazi extends CardImpl {

    public GuardianOfVituGhazi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}{W}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // Convoke
        this.addAbility(new ConvokeAbility());
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
    }

    private GuardianOfVituGhazi(final GuardianOfVituGhazi card) {
        super(card);
    }

    @Override
    public GuardianOfVituGhazi copy() {
        return new GuardianOfVituGhazi(this);
    }
}

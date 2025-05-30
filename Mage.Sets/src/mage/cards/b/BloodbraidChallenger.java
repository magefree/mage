

package mage.cards.b;

import mage.MageInt;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.EscapeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;


/**
 * @author Susucr
 */
public final class BloodbraidChallenger extends CardImpl {

    public BloodbraidChallenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");

        this.subtype.add(SubType.ELF, SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Cascade
        this.addAbility(new CascadeAbility());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Escape-{3}{R}{G}, Exile three other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{3}{R}{G}", 3));
    }

    private BloodbraidChallenger(final BloodbraidChallenger card) {
        super(card);
    }

    @Override
    public BloodbraidChallenger copy() {
        return new BloodbraidChallenger(this);
    }

}


package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class AnuridMurkdiver extends CardImpl {

    public AnuridMurkdiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        this.addAbility(new SwampwalkAbility());
    }

    private AnuridMurkdiver(final AnuridMurkdiver card) {
        super(card);
    }

    @Override
    public AnuridMurkdiver copy() {
        return new AnuridMurkdiver(this);
    }
}

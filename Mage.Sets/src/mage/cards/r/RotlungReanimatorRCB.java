
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.CephalidBlackToken;

/**
 *
 * @author mschatz
 */
public final class RotlungReanimatorRCB extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.RAT, "Rat");

    public RotlungReanimatorRCB(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CLERIC);

        // Absorb initial Infest and Dread of Night Black
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.subtype.add(SubType.ASSEMBLY_WORKER); // Apply Olivia Voldaren

        // Apply Prismatic Lace
        this.color.setBlack(true);
        this.color.setGreen(true);
        this.color.setRed(true);
        this.color.setWhite(true);

        // Whenever Rotlung Reanimator or another Rat dies, create a 2/2 black Cephalid creature token.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(new CreateTokenEffect(new CephalidBlackToken()), false, filter));
    }

    private RotlungReanimatorRCB(final RotlungReanimatorRCB card) {
        super(card);
    }

    @Override
    public RotlungReanimatorRCB copy() {
        return new RotlungReanimatorRCB(this);
    }
}

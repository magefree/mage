
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
public final class RotlungReanimatorLCB extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.LHURGOYF, "Lhurgoyf");

    public RotlungReanimatorLCB(UUID ownerId, CardSetInfo setInfo) {
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

        // Whenever Rotlung Reanimator or another Lhurgoyf dies, create a 2/2 black Cephalid creature token.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(new CreateTokenEffect(new CephalidBlackToken()), false, filter));
    }

    private RotlungReanimatorLCB(final RotlungReanimatorLCB card) {
        super(card);
    }

    @Override
    public RotlungReanimatorLCB copy() {
        return new RotlungReanimatorLCB(this);
    }
}

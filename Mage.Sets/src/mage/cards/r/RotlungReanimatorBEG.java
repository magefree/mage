
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
import mage.game.permanent.token.ElfGreenToken;

/**
 *
 * @author mschatz
 */
public final class RotlungReanimatorBEG extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.BASILISK, "Basilisk");

    public RotlungReanimatorBEG(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CLERIC);

        // Absorb initial Infest and Dread of Night Black
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Apply Prismatic Lace
        this.color.setBlack(true);
        this.color.setGreen(true);
        this.color.setRed(true);
        this.color.setWhite(true);

        // Whenever Rotlung Reanimator or another Basilisk dies, create a 2/2 green Elf creature token.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(new CreateTokenEffect(new ElfGreenToken()), false, filter));
    }

    private RotlungReanimatorBEG(final RotlungReanimatorBEG card) {
        super(card);
    }

    @Override
    public RotlungReanimatorBEG copy() {
        return new RotlungReanimatorBEG(this);
    }
}

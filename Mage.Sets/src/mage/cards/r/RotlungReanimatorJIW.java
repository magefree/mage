
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
import mage.game.permanent.token.IllusionWhiteToken;

/**
 *
 * @author mschatz
 */
public final class RotlungReanimatorJIW extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.JUGGERNAUT, "Juggernaut");

    public RotlungReanimatorJIW(UUID ownerId, CardSetInfo setInfo) {
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

        // Whenever Rotlung Reanimator or another Juggernaut dies, create a 2/2 white Illusion creature token.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(new CreateTokenEffect(new IllusionWhiteToken()), false, filter));
    }

    private RotlungReanimatorJIW(final RotlungReanimatorJIW card) {
        super(card);
    }

    @Override
    public RotlungReanimatorJIW copy() {
        return new RotlungReanimatorJIW(this);
    }
}

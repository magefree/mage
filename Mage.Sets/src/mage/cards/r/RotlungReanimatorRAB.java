
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
import mage.game.permanent.token.AssassinBlueToken;

/**
 *
 * @author mschatz
 */
public final class RotlungReanimatorRAB extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.RHINO, "Rhino");

    public RotlungReanimatorRAB(UUID ownerId, CardSetInfo setInfo) {
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

        // Whenever Rotlung Reanimator or another Rhino dies, create a 2/2 blue Assassin creature token.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(new CreateTokenEffect(new AssassinBlueToken()), false, filter));
    }

    private RotlungReanimatorRAB(final RotlungReanimatorRAB card) {
        super(card);
    }

    @Override
    public RotlungReanimatorRAB copy() {
        return new RotlungReanimatorRAB(this);
    }
}

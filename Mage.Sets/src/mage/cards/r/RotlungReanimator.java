
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
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author fireshoes
 */
public final class RotlungReanimator extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.CLERIC, "Cleric");

    public RotlungReanimator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Rotlung Reanimator or another Cleric dies, create a 2/2 black Zombie creature token.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(new CreateTokenEffect(new ZombieToken()), false, filter));
    }

    private RotlungReanimator(final RotlungReanimator card) {
        super(card);
    }

    @Override
    public RotlungReanimator copy() {
        return new RotlungReanimator(this);
    }
}

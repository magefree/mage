

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class DeathcurseOgre extends CardImpl {

    public DeathcurseOgre (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(new DiesSourceTriggeredAbility(new LoseLifeAllPlayersEffect(3)));
    }

    private DeathcurseOgre(final DeathcurseOgre card) {
        super(card);
    }

    @Override
    public DeathcurseOgre copy() {
        return new DeathcurseOgre(this);
    }

}

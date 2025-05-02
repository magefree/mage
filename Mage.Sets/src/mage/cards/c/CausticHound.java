

package mage.cards.c;

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
public final class CausticHound extends CardImpl {

    public CausticHound (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(new DiesSourceTriggeredAbility(new LoseLifeAllPlayersEffect(4)));
    }

    private CausticHound(final CausticHound card) {
        super(card);
    }

    @Override
    public CausticHound copy() {
        return new CausticHound(this);
    }
}


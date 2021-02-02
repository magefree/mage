
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author noxx
 */
public final class SoulcageFiend extends CardImpl {

    public SoulcageFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Soulcage Fiend dies, each player loses 3 life.
        this.addAbility(new DiesSourceTriggeredAbility(new LoseLifeAllPlayersEffect(3)));
    }

    private SoulcageFiend(final SoulcageFiend card) {
        super(card);
    }

    @Override
    public SoulcageFiend copy() {
        return new SoulcageFiend(this);
    }
}

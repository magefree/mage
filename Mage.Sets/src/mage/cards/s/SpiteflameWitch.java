
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class SpiteflameWitch extends CardImpl {

    public SpiteflameWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B/R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeAllPlayersEffect(1), new ManaCostsImpl<>("{B}{R}")));
    }

    private SpiteflameWitch(final SpiteflameWitch card) {
        super(card);
    }

    @Override
    public SpiteflameWitch copy() {
        return new SpiteflameWitch(this);
    }
}

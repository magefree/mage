
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlankingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class MtendaHerder extends CardImpl {

    public MtendaHerder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flanking
        this.addAbility(new FlankingAbility());
    }

    private MtendaHerder(final MtendaHerder card) {
        super(card);
    }

    @Override
    public MtendaHerder copy() {
        return new MtendaHerder(this);
    }
}

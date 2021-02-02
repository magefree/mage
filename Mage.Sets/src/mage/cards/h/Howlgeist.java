
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesWithLessPowerEffect;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class Howlgeist extends CardImpl {

    public Howlgeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.WOLF);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Creatures with power less than Howlgeist's power can't block it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByCreaturesWithLessPowerEffect()));
        this.addAbility(new UndyingAbility());
    }

    private Howlgeist(final Howlgeist card) {
        super(card);
    }

    @Override
    public Howlgeist copy() {
        return new Howlgeist(this);
    }
}

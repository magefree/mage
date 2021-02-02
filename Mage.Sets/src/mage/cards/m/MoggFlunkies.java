
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantAttackAloneAbility;
import mage.abilities.keyword.CantBlockAloneAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author magenoxx_at_gmail.com
 */
public final class MoggFlunkies extends CardImpl {

    public MoggFlunkies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Mogg Flunkies can't attack or block alone.
        this.addAbility(new CantAttackAloneAbility());
        this.addAbility(CantBlockAloneAbility.getInstance());
    }

    private MoggFlunkies(final MoggFlunkies card) {
        super(card);
    }

    @Override
    public MoggFlunkies copy() {
        return new MoggFlunkies(this);
    }
}

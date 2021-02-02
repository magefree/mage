
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class SightlessGhoul extends CardImpl {

    public SightlessGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sightless Ghoul can't block.
        this.addAbility(new CantBlockAbility());
        // Undying
        this.addAbility(new UndyingAbility());
    }

    private SightlessGhoul(final SightlessGhoul card) {
        super(card);
    }

    @Override
    public SightlessGhoul copy() {
        return new SightlessGhoul(this);
    }
}

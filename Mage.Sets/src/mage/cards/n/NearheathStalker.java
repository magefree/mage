
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class NearheathStalker extends CardImpl {

    public NearheathStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Undying
        this.addAbility(new UndyingAbility());
    }

    private NearheathStalker(final NearheathStalker card) {
        super(card);
    }

    @Override
    public NearheathStalker copy() {
        return new NearheathStalker(this);
    }
}

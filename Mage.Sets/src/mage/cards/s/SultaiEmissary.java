
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class SultaiEmissary extends CardImpl {

    public SultaiEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Sultai Emissary dies, manifest the top card of your library.
        this.addAbility(new DiesSourceTriggeredAbility(new ManifestEffect(1)));
    }

    private SultaiEmissary(final SultaiEmissary card) {
        super(card);
    }

    @Override
    public SultaiEmissary copy() {
        return new SultaiEmissary(this);
    }
}


package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class NyxFleeceRam extends CardImpl {

    public NyxFleeceRam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.SHEEP);

        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // At the beginning of your upkeep, you gain 1 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GainLifeEffect(1)));
    }

    private NyxFleeceRam(final NyxFleeceRam card) {
        super(card);
    }

    @Override
    public NyxFleeceRam copy() {
        return new NyxFleeceRam(this);
    }
}

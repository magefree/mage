
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author LoneFox
 */
public final class NettletoothDjinn extends CardImpl {

    public NettletoothDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, Nettletooth Djinn deals 1 damage to you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DamageControllerEffect(1), TargetController.YOU, false));                                                                                 }

    private NettletoothDjinn(final NettletoothDjinn card) {
        super(card);
    }

    @Override
    public NettletoothDjinn copy() {
        return new NettletoothDjinn(this);
    }
}

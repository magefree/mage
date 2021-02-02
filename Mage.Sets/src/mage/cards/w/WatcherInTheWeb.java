
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class WatcherInTheWeb extends CardImpl {

    public WatcherInTheWeb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Watcher in the Web can block an additional seven creatures each combat.
        Effect effect = new CanBlockAdditionalCreatureEffect(7);
        effect.setText("{this} can block an additional seven creatures each combat");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private WatcherInTheWeb(final WatcherInTheWeb card) {
        super(card);
    }

    @Override
    public WatcherInTheWeb copy() {
        return new WatcherInTheWeb(this);
    }
}

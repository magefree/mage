

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.DealtDamageToCreatureBySourceDies;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.watchers.common.DamagedByWatcher;

/**
 * @author LevelX
 */
public final class KumanosPupils extends CardImpl {

    public KumanosPupils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // If a creature dealt damage by Kumano's Pupils this turn would die, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DealtDamageToCreatureBySourceDies(this, Duration.WhileOnBattlefield)), new DamagedByWatcher(false));
    }

    private KumanosPupils(final KumanosPupils card) {
        super(card);
    }

    @Override
    public KumanosPupils copy() {
        return new KumanosPupils(this);
    }

}

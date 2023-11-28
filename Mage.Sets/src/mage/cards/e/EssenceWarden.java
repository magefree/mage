
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class EssenceWarden extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");
    static {
        filter.add(AnotherPredicate.instance);
    }

    public EssenceWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another creature enters the battlefield, you gain 1 life.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new GainLifeEffect(1), filter, false));
    }

    private EssenceWarden(final EssenceWarden card) {
        super(card);
    }

    @Override
    public EssenceWarden copy() {
        return new EssenceWarden(this);
    }
}

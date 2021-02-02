
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterArtifactPermanent;

/**
 *
 * @author Plopman
 */
public final class MoriokRigger extends CardImpl {

    public MoriokRigger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.RIGGER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever an artifact is put into a graveyard from the battlefield, you may put a +1/+1 counter on Moriok Rigger.
         Ability ability = new ZoneChangeAllTriggeredAbility(Zone.BATTLEFIELD, Zone.BATTLEFIELD, Zone.GRAVEYARD,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), new FilterArtifactPermanent(),
                "Whenever an artifact is put into a graveyard from the battlefield, ", true);
         this.addAbility(ability);
    }

    private MoriokRigger(final MoriokRigger card) {
        super(card);
    }

    @Override
    public MoriokRigger copy() {
        return new MoriokRigger(this);
    }
}

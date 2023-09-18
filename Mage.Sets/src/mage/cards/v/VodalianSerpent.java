
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author LoneFox

 */
public final class VodalianSerpent extends CardImpl {

    public VodalianSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {2}
        this.addAbility(new KickerAbility("{2}"));
        // Vodalian Serpent can't attack unless defending player controls an Island.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackUnlessDefenderControllsPermanent(new FilterLandPermanent(SubType.ISLAND, "an Island"))));
        // If Vodalian Serpent was kicked, it enters the battlefield with four +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(4)),
            KickedCondition.ONCE, "If {this} was kicked, it enters the battlefield with four +1/+1 counters on it.", ""));
    }

    private VodalianSerpent(final VodalianSerpent card) {
        super(card);
    }

    @Override
    public VodalianSerpent copy() {
        return new VodalianSerpent(this);
    }
}

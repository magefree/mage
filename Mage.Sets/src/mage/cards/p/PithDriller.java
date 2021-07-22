
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class PithDriller extends CardImpl {

    public PithDriller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}{B/P}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private PithDriller(final PithDriller card) {
        super(card);
    }

    @Override
    public PithDriller copy() {
        return new PithDriller(this);
    }
}

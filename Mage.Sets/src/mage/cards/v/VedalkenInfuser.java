

package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author Loki
 */
public final class VedalkenInfuser extends CardImpl {

    public VedalkenInfuser (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, you may put a charge counter on target artifact.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new AddCountersTargetEffect(CounterType.CHARGE.createInstance()), TargetController.YOU, true);
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private VedalkenInfuser(final VedalkenInfuser card) {
        super(card);
    }

    @Override
    public VedalkenInfuser copy() {
        return new VedalkenInfuser(this);
    }

}

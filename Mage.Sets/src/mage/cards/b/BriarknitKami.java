
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class BriarknitKami extends CardImpl {

    public BriarknitKami(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        // Whenever you cast a Spirit or Arcane spell, put a +1/+1 counter on target creature.
        Ability ability = new SpellCastControllerTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BriarknitKami(final BriarknitKami card) {
        super(card);
    }

    @Override
    public BriarknitKami copy() {
        return new BriarknitKami(this);
    }
}


package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author L_J
 */
public final class ForcemageAdvocate extends CardImpl {

    public ForcemageAdvocate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {tap}: Return target card from an opponent's graveyard to their hand. Put a +1/+1 counter on target creature.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        effect.setText("Return target card from an opponent's graveyard to their hand");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());

        effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        effect.setText("Put a +1/+1 counter on target creature");
        effect.setTargetPointer(new SecondTargetPointer());
        ability.addEffect(effect);
        ability.addTarget(new TargetCardInOpponentsGraveyard(1, 1, new FilterCard(), true));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public ForcemageAdvocate(final ForcemageAdvocate card) {
        super(card);
    }

    @Override
    public ForcemageAdvocate copy() {
        return new ForcemageAdvocate(this);
    }
}

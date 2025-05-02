package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DealtDamageAnyTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TerminationFacilitator extends CardImpl {

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent("a creature or planeswalker an opponent controls with a bounty counter on it");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(CounterType.BOUNTY.getPredicate());
    }

    public TerminationFacilitator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Put a bounty counter on target creature or planeswalker. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()), new TapSourceCost()
        );
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);

        // Whenever a creature or planeswalker an opponent controls with a bounty counter on it is dealt damage, destroy it.
        this.addAbility(new DealtDamageAnyTriggeredAbility(new DestroyTargetEffect().setText("destroy it"),
                filter, SetTargetPointer.PERMANENT, false));
    }

    private TerminationFacilitator(final TerminationFacilitator card) {
        super(card);
    }

    @Override
    public TerminationFacilitator copy() {
        return new TerminationFacilitator(this);
    }
}

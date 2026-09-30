package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAsItEntersChooseColorAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.mana.AddManaChosenColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class RoomOfRefuge extends CardImpl {

    public RoomOfRefuge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped. As it enters, choose a color.
        this.addAbility(new EntersBattlefieldTappedAsItEntersChooseColorAbility());

        // {T}: Add one mana of the chosen color.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaChosenColorEffect(), new TapSourceCost()));

        // {5}, {T}, Sacrifice this land: Put two +1/+1 counters on target creature. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)), new ManaCostsImpl<>("{5}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RoomOfRefuge(final RoomOfRefuge card) {
        super(card);
    }

    @Override
    public RoomOfRefuge copy() {
        return new RoomOfRefuge(this);
    }
}

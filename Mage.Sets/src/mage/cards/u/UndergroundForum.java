package mage.cards.u;

import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class UndergroundForum extends CardImpl {

    public UndergroundForum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // T: Add {1}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Add {B}, {R}, or {G}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(1), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(1), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(1), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {2}, {T}: Put a bounty counter on target creature.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()), new GenericManaCost(2));
        ability2.addCost(new TapSourceCost());
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);
    }

    public UndergroundForum(final UndergroundForum card) {
        super(card);
    }

    @Override
    public UndergroundForum copy() {
        return new UndergroundForum(this);
    }
}

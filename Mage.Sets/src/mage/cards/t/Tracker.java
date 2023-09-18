
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageEachOtherEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class Tracker extends CardImpl {

    public Tracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {G}{G}, {tap}: Tracker deals damage equal to its power to target creature. That creature deals damage equal to its power to Tracker.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageEachOtherEffect(), new ManaCostsImpl<>("{G}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Tracker(final Tracker card) {
        super(card);
    }

    @Override
    public Tracker copy() {
        return new Tracker(this);
    }
}

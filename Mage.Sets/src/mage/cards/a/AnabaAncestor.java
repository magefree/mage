
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class AnabaAncestor extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Another target Minotaur creature");

    static {
        filter.add(SubType.MINOTAUR.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public AnabaAncestor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Another target Minotaur creature gets +1/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(1, 1, Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private AnabaAncestor(final AnabaAncestor card) {
        super(card);
    }

    @Override
    public AnabaAncestor copy() {
        return new AnabaAncestor(this);
    }
}

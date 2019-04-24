
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.SetPowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class SerendibSorcerer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature other than {this}");

    static {
        filter.add(new AnotherPredicate());
    }

    public SerendibSorcerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Target creature other than Serendib Sorcerer becomes 0/2 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SetPowerToughnessTargetEffect(0, 2, Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public SerendibSorcerer(final SerendibSorcerer card) {
        super(card);
    }

    @Override
    public SerendibSorcerer copy() {
        return new SerendibSorcerer(this);
    }
}

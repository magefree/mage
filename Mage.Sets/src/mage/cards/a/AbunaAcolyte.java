
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Martin Sagastume msr986@gmail.com
 */
public final class AbunaAcolyte extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("artifact creature");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public AbunaAcolyte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, 1), new TapSourceCost());
        ability1.addTarget(new TargetAnyTarget());
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, 2), new TapSourceCost());
        ability2.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability1);
        this.addAbility(ability2);
    }

    private AbunaAcolyte(final AbunaAcolyte card) {
        super(card);
    }

    @Override
    public AbunaAcolyte copy() {
        return new AbunaAcolyte(this);
    }

}
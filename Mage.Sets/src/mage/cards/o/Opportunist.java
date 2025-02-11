

package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.WasDealtDamageThisTurnPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author LevelX
 */
public final class Opportunist extends CardImpl {

    private static final  FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that was dealt damage this turn");

    static {
        filter.add(WasDealtDamageThisTurnPredicate.instance);
    }

    public Opportunist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Opportunist deals 1 damage to target creature that was dealt damage this turn.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private Opportunist(final Opportunist card) {
        super(card);
    }

    @Override
    public Opportunist copy() {
        return new Opportunist(this);
    }

}

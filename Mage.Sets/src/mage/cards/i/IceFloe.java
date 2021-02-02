
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DontUntapAsLongAsSourceTappedEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.common.FilterCreatureAttackingYou;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 *
 * @author TheElk801
 */
public final class IceFloe extends CardImpl {

    private static final FilterCreatureAttackingYou filter = new FilterCreatureAttackingYou("creature without flying that's attacking you");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public IceFloe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // You may choose not to untap Ice Floe during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {T}: Tap target creature without flying that's attacking you. It doesn't untap during its controller's untap step for as long as Ice Floe remains tapped.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.addEffect(new DontUntapAsLongAsSourceTappedEffect());
        this.addAbility(ability);
    }

    private IceFloe(final IceFloe card) {
        super(card);
    }

    @Override
    public IceFloe copy() {
        return new IceFloe(this);
    }
}

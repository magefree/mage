
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX
 */
public final class NineRingedBo extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("spirit");

    static {
        filter.add(new SubtypePredicate(SubType.SPIRIT));
    }

    public NineRingedBo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Nine-Ringed Bo deals 1 damage to target Spirit creature. If that creature would die this turn, exile it instead.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
        ability.addEffect(new ExileTargetIfDiesEffect());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public NineRingedBo(final NineRingedBo card) {
        super(card);
    }

    @Override
    public NineRingedBo copy() {
        return new NineRingedBo(this);
    }
}

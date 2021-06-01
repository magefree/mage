package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX
 */
public final class NineRingedBo extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.SPIRIT, "Spirit creature");

    public NineRingedBo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Nine-Ringed Bo deals 1 damage to target Spirit creature. If that creature would die this turn, exile it instead.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new TapSourceCost());
        ability.addEffect(new ExileTargetIfDiesEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private NineRingedBo(final NineRingedBo card) {
        super(card);
    }

    @Override
    public NineRingedBo copy() {
        return new NineRingedBo(this);
    }
}

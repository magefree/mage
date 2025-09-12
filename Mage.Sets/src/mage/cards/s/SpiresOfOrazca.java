package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.RemoveFromCombatTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpiresOfOrazca extends CardImpl {

    private static final FilterPermanent filter = new FilterOpponentsCreaturePermanent("attacking creature an opponent controls");

    static {
        filter.add(AttackingPredicate.instance);
    }

    public SpiresOfOrazca(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.nightCard = true;

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Untap target attacking creature an opponent controls and remove it from combat.
        Ability ability = new SimpleActivatedAbility(
                new UntapTargetEffect()
                        .setText("Untap target attacking creature an opponent controls"),
                new TapSourceCost()
        );
        ability.addEffect(new RemoveFromCombatTargetEffect().setText("and remove it from combat"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SpiresOfOrazca(final SpiresOfOrazca card) {
        super(card);
    }

    @Override
    public SpiresOfOrazca copy() {
        return new SpiresOfOrazca(this);
    }
}

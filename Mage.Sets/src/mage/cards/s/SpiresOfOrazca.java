
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RemoveFromCombatTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class SpiresOfOrazca extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking creature an opponent controls");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public SpiresOfOrazca(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.nightCard = true;

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Untap target attacking creature an opponent controls and remove it from combat.
        Effect effect = new UntapTargetEffect();
        effect.setText("Untap target attacking creature an opponent controls and remove it from combat.");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        effect = new RemoveFromCombatTargetEffect();
        effect.setText(" ");
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent(filter));
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

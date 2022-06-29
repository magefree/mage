
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author spjspj
 */
public final class Reconnaissance extends CardImpl {

    private static FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("attacking creature controlled by you");

    static {
        filter.add(AttackingPredicate.instance);
    }

    public Reconnaissance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");

        // {0}: Remove target attacking creature you control from combat and untap it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReconnaissanceRemoveFromCombatEffect(), new ManaCostsImpl<>("{0}"));
        ability.addTarget(new TargetControlledCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private Reconnaissance(final Reconnaissance card) {
        super(card);
    }

    @Override
    public Reconnaissance copy() {
        return new Reconnaissance(this);
    }
}

class ReconnaissanceRemoveFromCombatEffect extends OneShotEffect {

    public ReconnaissanceRemoveFromCombatEffect() {
        super(Outcome.Benefit);
        this.staticText = "Remove target attacking creature you control from combat and untap it";
    }

    public ReconnaissanceRemoveFromCombatEffect(final ReconnaissanceRemoveFromCombatEffect effect) {
        super(effect);
    }

    @Override
    public ReconnaissanceRemoveFromCombatEffect copy() {
        return new ReconnaissanceRemoveFromCombatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());

        if (creature != null && player != null && creature.isAttacking()) {
            creature.removeFromCombat(game);
            creature.untap(game);
            return true;
        }
        return false;
    }
}

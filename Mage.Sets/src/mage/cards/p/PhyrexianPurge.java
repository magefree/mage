
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DestroyMultiTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author escplan9 - Derek Monturo
 */
public final class PhyrexianPurge extends CardImpl {

    public PhyrexianPurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{R}");

        // Destroy any number of target creatures.
        // Phyrexian Purge costs 3 life more to cast for each target.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
        this.getSpellAbility().addEffect(new DestroyMultiTargetEffect());
        this.getSpellAbility().addEffect(new InfoEffect("<br><br>{this} costs 3 life more to cast for each target"));
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int numTargets = ability.getTargets().get(0).getTargets().size();
        if (numTargets > 0) {
            ability.getCosts().add(new PayLifeCost(numTargets * 3));
        }
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            Player you = game.getPlayer(ownerId);
            if(you != null) {
                int maxTargets = you.getLife() / 3;
                ability.addTarget(new TargetCreaturePermanent(0, maxTargets));
            }
        }
    }

    public PhyrexianPurge(final PhyrexianPurge card) {
        super(card);
    }

    @Override
    public PhyrexianPurge copy() {
        return new PhyrexianPurge(this);
    }
}

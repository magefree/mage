
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EscalateAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class CollectiveDefiance extends CardImpl {

    private static final FilterPlayer filterDiscard = new FilterPlayer("player to discard and then draw cards");
    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("creature to be dealt damage");

    public CollectiveDefiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Escalate {1}
        this.addAbility(new EscalateAbility(new GenericManaCost(1)));

        // Choose one or more &mdash;
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(3);

        // Target player discards all cards in their hand, then draws that many cards.;
        this.getSpellAbility().addEffect(new CollectiveDefianceEffect());
        this.getSpellAbility().addTarget(new TargetPlayer(1, 1, false, filterDiscard));

        // Collective Defiance deals 4 damage to target creature.;
        Mode mode = new Mode();
        Effect effect = new DamageTargetEffect(4);
        effect.setText("{this} deals 4 damage to target creature");
        mode.getEffects().add(effect);
        mode.getTargets().add(new TargetCreaturePermanent(filterCreature));
        this.getSpellAbility().addMode(mode);

        // Collective Defiance deals 3 damage to target opponent or planeswalker.
        mode = new Mode();
        effect = new DamageTargetEffect(3);
        effect.setText("{this} deals 3 damage to target opponent or planeswalker");
        mode.getEffects().add(effect);
        mode.getTargets().add(new TargetOpponentOrPlaneswalker());
        this.getSpellAbility().addMode(mode);
    }

    public CollectiveDefiance(final CollectiveDefiance card) {
        super(card);
    }

    @Override
    public CollectiveDefiance copy() {
        return new CollectiveDefiance(this);
    }
}

class CollectiveDefianceEffect extends OneShotEffect {

    public CollectiveDefianceEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player discards all the cards in their hand, then draws that many cards";
    }

    public CollectiveDefianceEffect(final CollectiveDefianceEffect effect) {
        super(effect);
    }

    @Override
    public CollectiveDefianceEffect copy() {
        return new CollectiveDefianceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            int count = targetPlayer.getHand().size();
            for (Card card : targetPlayer.getHand().getCards(game)) {
                targetPlayer.discard(card, source, game);
            }
            targetPlayer.drawCards(count, game);
            return true;
        }
        return false;
    }
}

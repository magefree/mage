package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.JumpStartAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GraviticPunch extends CardImpl {

    public GraviticPunch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Target creature you control deals damage equal to its power to target player.
        this.getSpellAbility().addEffect(new GraviticPunchEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Jump-start
        this.addAbility(new JumpStartAbility(this));

    }

    private GraviticPunch(final GraviticPunch card) {
        super(card);
    }

    @Override
    public GraviticPunch copy() {
        return new GraviticPunch(this);
    }
}

class GraviticPunchEffect extends OneShotEffect {

    public GraviticPunchEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target creature you control deals damage "
                + "equal to its power to target player.";
    }

    private GraviticPunchEffect(final GraviticPunchEffect effect) {
        super(effect);
    }

    @Override
    public GraviticPunchEffect copy() {
        return new GraviticPunchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent controlledCreature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        Player player = game.getPlayer(source.getTargets().get(1).getFirstTarget());
        if (player == null || controlledCreature == null) {
            return false;
        }
        player.damage(controlledCreature.getPower().getValue(), controlledCreature.getId(), source, game);
        return true;
    }
}

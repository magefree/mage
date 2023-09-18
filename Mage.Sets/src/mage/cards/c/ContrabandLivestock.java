package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Boar2Token;
import mage.game.permanent.token.GoatToken;
import mage.game.permanent.token.OxGreenToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ContrabandLivestock extends CardImpl {

    public ContrabandLivestock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Exile target creature, then roll a d20.
        // 1-9 | That creature's controller creates a 4/4 green Ox creature token.
        // 10-19 | That creature's controller creates a 2/2 green Boar creature token.
        // 20 | That creature's controller creates a 0/1 white Goat creature token.
        this.getSpellAbility().addEffect(new ContrabandLivestockEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ContrabandLivestock(final ContrabandLivestock card) {
        super(card);
    }

    @Override
    public ContrabandLivestock copy() {
        return new ContrabandLivestock(this);
    }
}

class ContrabandLivestockEffect extends RollDieWithResultTableEffect {

    ContrabandLivestockEffect() {
        super(20, "exile target creature, then roll a d20");
        this.addTableEntry(1, 9, new CreateTokenTargetEffect(new OxGreenToken())
                .setText("its controller creates a 4/4 green Ox creature token"));
        this.addTableEntry(10, 19, new CreateTokenTargetEffect(new Boar2Token())
                .setText("its controller creates a 2/2 green Boar creature token"));
        this.addTableEntry(20, 20, new CreateTokenTargetEffect(new GoatToken())
                .setText("its controller creates a 0/1 white Goat creature token"));
    }

    private ContrabandLivestockEffect(final ContrabandLivestockEffect effect) {
        super(effect);
    }

    @Override
    public ContrabandLivestockEffect copy() {
        return new ContrabandLivestockEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        player.moveCards(permanent, Zone.EXILED, source, game);
        int result = player.rollDice(outcome, source, game, sides);
        this.setTargetPointer(new FixedTarget(permanent.getControllerId()));
        this.applyResult(result, game, source);
        return true;
    }
}

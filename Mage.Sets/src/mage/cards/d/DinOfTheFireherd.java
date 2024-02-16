
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.token.DinOfTheFireherdToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class DinOfTheFireherd extends CardImpl {

    public DinOfTheFireherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B/R}{B/R}{B/R}");

        // Create a 5/5 black and red Elemental creature token. Target opponent sacrifices a creature for each black creature you control, then sacrifices a land for each red creature you control.
        this.getSpellAbility().addEffect(new DinOfTheFireherdEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private DinOfTheFireherd(final DinOfTheFireherd card) {
        super(card);
    }

    @Override
    public DinOfTheFireherd copy() {
        return new DinOfTheFireherd(this);
    }
}

class DinOfTheFireherdEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent blackCreatureFilter = new FilterControlledCreaturePermanent("black creatures you control");
    private static final FilterControlledCreaturePermanent redCreatureFilter = new FilterControlledCreaturePermanent("red creatures you control");

    static {
        blackCreatureFilter.add(new ColorPredicate(ObjectColor.BLACK));
        redCreatureFilter.add(new ColorPredicate(ObjectColor.RED));
    }

    public DinOfTheFireherdEffect() {
        super(Outcome.Neutral);
        this.staticText = "create a 5/5 black and red Elemental creature token. Target opponent sacrifices a creature for each black creature you control, then sacrifices a land for each red creature you control";
    }

    private DinOfTheFireherdEffect(final DinOfTheFireherdEffect effect) {
        super(effect);
    }

    @Override
    public DinOfTheFireherdEffect copy() {
        return new DinOfTheFireherdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied;
        Token token = new DinOfTheFireherdToken();
        applied = token.putOntoBattlefield(1, game, source, source.getControllerId());

        int blackCreaturesControllerControls = game.getBattlefield().countAll(blackCreatureFilter, source.getControllerId(), game);
        int redCreaturesControllerControls = game.getBattlefield().countAll(redCreatureFilter, source.getControllerId(), game);

        Player targetOpponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetOpponent != null) {
            Effect effect = new SacrificeEffect(new FilterControlledCreaturePermanent(), blackCreaturesControllerControls, "Target Opponent");
            effect.setTargetPointer(new FixedTarget(targetOpponent.getId()));
            effect.apply(game, source);

            Effect effect2 = new SacrificeEffect(new FilterControlledLandPermanent(), redCreaturesControllerControls, "Target Opponent");
            effect2.setTargetPointer(new FixedTarget(targetOpponent.getId()));
            effect2.apply(game, source);
            applied = true;
        }
        return applied;
    }
}

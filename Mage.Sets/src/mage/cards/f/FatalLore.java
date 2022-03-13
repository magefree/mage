package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class FatalLore extends CardImpl {

    public FatalLore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // An opponent chooses one - You draw three cards; or you destroy up to two target creatures that opponent controls and that player draws up to three cards. Those creatures can't be regenerated.
        this.getSpellAbility().addEffect(new FatalLoreEffect());
        this.getSpellAbility().addTarget(new TargetOpponent(true));

    }

    private FatalLore(final FatalLore card) {
        super(card);
    }

    @Override
    public FatalLore copy() {
        return new FatalLore(this);
    }
}

class FatalLoreEffect extends OneShotEffect {

    public FatalLoreEffect() {
        super(Outcome.Neutral);
        staticText = "An opponent chooses one - You draw three cards; or you destroy up to two target creatures that opponent controls and that player draws up to three cards. Those creatures can't be regenerated";
    }

    public FatalLoreEffect(final FatalLoreEffect effect) {
        super(effect);
    }

    @Override
    public FatalLoreEffect copy() {
        return new FatalLoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player chosenOpponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller != null
                && chosenOpponent != null) {
            if (chosenOpponent.chooseUse(Outcome.Neutral, "If you choose Yes, the controller draws three cards. If no, the controller gets to destroy up to two target creatures that you control and you get to draw up to 3 cards. Those creatures can't be regenerated.", source, game)) {
                controller.drawCards(3, source, game);
            } else {
                FilterCreaturePermanent filter = new FilterCreaturePermanent("chosen opponent's creature");
                filter.add(new ControllerIdPredicate(chosenOpponent.getId()));
                TargetCreaturePermanent target = new TargetCreaturePermanent(0, 2, filter, false);
                if (target.canChoose(controller.getId(), source, game)
                        && controller.choose(Outcome.DestroyPermanent, target, source, game)) {
                    for (UUID targetId : target.getTargets()) {
                        Effect destroyCreature = new DestroyTargetEffect(true);
                        destroyCreature.setTargetPointer(new FixedTarget(targetId, game));
                        destroyCreature.apply(game, source);
                    }
                    Effect opponentDrawsCards = new DrawCardTargetEffect(StaticValue.get(3), false, true);
                    opponentDrawsCards.setTargetPointer(new FixedTarget(chosenOpponent.getId()));
                    opponentDrawsCards.apply(game, source);
                    return true;
                }
            }
        }
        return false;
    }
}

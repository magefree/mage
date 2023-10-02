package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class LaezelsAcrobatics extends CardImpl {

    public LaezelsAcrobatics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        this.getSpellAbility().addEffect(new LaezelsAcrobaticsEffect());
    }

    private LaezelsAcrobatics(final LaezelsAcrobatics card) {
        super(card);
    }

    @Override
    public LaezelsAcrobatics copy() {
        return new LaezelsAcrobatics(this);
    }
}

class LaezelsAcrobaticsEffect extends RollDieWithResultTableEffect {

    private static final FilterControlledCreaturePermanent creatureFilter = new FilterControlledCreaturePermanent();

    static {
        creatureFilter.add(TokenPredicate.FALSE);
    }

    LaezelsAcrobaticsEffect() {
        super(20, "Exile all nontoken creatures you control, then roll a d20");
        this.addTableEntry(
                1, 9,
                new InfoEffect("Return those cards to the battlefield under their owner's control at the " +
                        "beginning of the next end step."));
        this.addTableEntry(
                10, 20,
                new InfoEffect(
                        "Return those cards to the battlefield under their owner's control, then exile them again. Return those cards to the battlefield under their owner's control at the beginning of the next end step."));
    }

    private LaezelsAcrobaticsEffect(final LaezelsAcrobaticsEffect effect) {
        super(effect);
    }

    @Override
    public LaezelsAcrobaticsEffect copy() {
        return new LaezelsAcrobaticsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards toExile = new CardsImpl(game.getBattlefield().getActivePermanents(creatureFilter, player.getId(), game));
        int result = player.rollDice(outcome, source, game, 20);
        if (result >= 1 && result <= 9) {
            Effect effect = new ExileReturnBattlefieldNextEndStepTargetEffect();
            effect.setTargetPointer(new FixedTargets(toExile, game));
            effect.apply(game, source);
        } else if (result >= 10 && result <= 20) {
            Effect effect = new ExileThenReturnTargetEffect(false, true);
            effect.setTargetPointer(new FixedTargets(toExile, game));
            effect.apply(game, source);
            game.getState().processAction(game);
            Effect effect2 = new ExileReturnBattlefieldNextEndStepTargetEffect();
            effect2.setTargetPointer(new FixedTargets(toExile, game));
            effect2.apply(game, source);
        }
        return true;
    }
}

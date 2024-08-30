package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Sidorovich77
 */
public final class SixSidedDie extends CardImpl {

    public SixSidedDie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Choose target creature. Roll a six-sided die.
        //
        //1 — It has base toughness 1 until end of turn.
        //
        //2 — Put two -1/-1 counters on it.
        //
        //3 — Six-Sided Die deals 3 damage to it, and you gain 3 life.
        //
        //4 — It gets -4/-4 until end of turn.
        //
        //5 — Destroy it.
        //
        //6 — Exile it.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new SixSidedDieEffect());
    }

    private SixSidedDie(final SixSidedDie card) {
        super(card);
    }

    @Override
    public SixSidedDie copy() {
        return new SixSidedDie(this);
    }
}

class SixSidedDieEffect extends OneShotEffect {

    SixSidedDieEffect() {
        super(Outcome.Detriment);
        setText("choose target creature. Roll a six-sided die." +
                "<br>1 — It has base toughness 1 until end of turn." +
                "<br>2 — Put two -1/-1 counters on it." +
                "<br>3 — {this} deals 3 damage to it and you gain 3 life." +
                "<br>4 — It gets -4/-4 until end of turn." +
                "<br>5 — Destroy it." +
                "<br>6 — Exile it.");
    }

    private SixSidedDieEffect(final SixSidedDieEffect effect) {
        super(effect);
    }

    @Override
    public SixSidedDieEffect copy() {
        return new SixSidedDieEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        int result = player.rollDice(outcome, source, game, 6);
        switch (result) {
            case 1:
                //Based on Chariot of the Sun
                game.addEffect(new SixSidedDieToughnessEffect(), source);
                break;
            case 2:
                permanent.addCounters(CounterType.M1M1.createInstance(2), source, game);
                break;
            case 3:
                permanent.damage(3, source, game);
                player.gainLife(3, game, source);
                break;
            case 4:
                game.addEffect(new BoostTargetEffect(-4, -4, Duration.EndOfTurn), source);
                break;
            case 5:
                permanent.destroy(source, game);
                break;
            case 6:
                player.moveCards(permanent, Zone.EXILED, source, game);
                break;
        }
        return true;
    }
}
class SixSidedDieToughnessEffect extends ContinuousEffectImpl {

    SixSidedDieToughnessEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.UnboostCreature);
    }

    private SixSidedDieToughnessEffect(final SixSidedDieToughnessEffect effect) {
        super(effect);
    }

    @Override
    public SixSidedDieToughnessEffect copy() {
        return new SixSidedDieToughnessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        permanent.getToughness().setModifiedBaseValue(1);
        return true;
    }
}

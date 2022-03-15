package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BenevolentOffering extends CardImpl {

    public BenevolentOffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Choose an opponent. You and that player each create three 1/1 white Spirit creature tokens with flying.
        this.getSpellAbility().addEffect(new BenevolentOfferingEffect1());

        // Choose an opponent. You gain 2 life for each creature you control and that player gains 2 life for each creature they control.
        this.getSpellAbility().addEffect(new BenevolentOfferingEffect2());
    }

    private BenevolentOffering(final BenevolentOffering card) {
        super(card);
    }

    @Override
    public BenevolentOffering copy() {
        return new BenevolentOffering(this);
    }
}

class BenevolentOfferingEffect1 extends OneShotEffect {

    BenevolentOfferingEffect1() {
        super(Outcome.Sacrifice);
        this.staticText = "Choose an opponent. You and that player each create three 1/1 white Spirit creature tokens with flying";
    }

    BenevolentOfferingEffect1(final BenevolentOfferingEffect1 effect) {
        super(effect);
    }

    @Override
    public BenevolentOfferingEffect1 copy() {
        return new BenevolentOfferingEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }

        Target target = new TargetOpponent(true);
        target.choose(Outcome.Sacrifice, source.getControllerId(), source.getSourceId(), game);
        Player opponent = game.getPlayer(target.getFirstTarget());
        if (opponent == null) { return false; }

        Effect effect = new CreateTokenTargetEffect(new SpiritWhiteToken(), 3);
        effect.setTargetPointer(new FixedTarget(opponent.getId()));
        effect.apply(game, source);
        new CreateTokenEffect(new SpiritWhiteToken(), 3).apply(game, source);
        return true;
    }
}

class BenevolentOfferingEffect2 extends OneShotEffect {

    BenevolentOfferingEffect2() {
        super(Outcome.Sacrifice);
        this.staticText = "<br>Choose an opponent. You gain 2 life for each creature you control and that player gains 2 life for each creature they control";
    }

    BenevolentOfferingEffect2(final BenevolentOfferingEffect2 effect) {
        super(effect);
    }

    @Override
    public BenevolentOfferingEffect2 copy() {
        return new BenevolentOfferingEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }

        Target target = new TargetOpponent(true);
        target.choose(Outcome.Sacrifice, source.getControllerId(), source.getSourceId(), game);
        Player opponent = game.getPlayer(target.getFirstTarget());
        if (opponent == null) { return false; }

        int count = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, controller.getId(), game) * 2;
        controller.gainLife(count, game, source);
        count = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, opponent.getId(), game) * 2;
        opponent.gainLife(count, game, source);

        return true;
    }
}

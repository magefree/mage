package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.ElfWarriorToken;
import mage.game.permanent.token.SylvanOfferingTreefolkToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SylvanOffering extends CardImpl {

    public SylvanOffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}");

        // Choose an opponent. You and that player each create an X/X green Treefolk creature token.
        this.getSpellAbility().addEffect(new SylvanOfferingEffect1());
        // Choose an opponent. You and that player each create X 1/1 green Elf Warrior creature tokens.
        this.getSpellAbility().addEffect(new SylvanOfferingEffect2());
    }

    private SylvanOffering(final SylvanOffering card) {
        super(card);
    }

    @Override
    public SylvanOffering copy() {
        return new SylvanOffering(this);
    }
}

class SylvanOfferingEffect1 extends OneShotEffect {

    SylvanOfferingEffect1() {
        super(Outcome.Sacrifice);
        this.staticText = "Choose an opponent. You and that player each create an X/X green Treefolk creature token";
    }

    SylvanOfferingEffect1(final SylvanOfferingEffect1 effect) {
        super(effect);
    }

    @Override
    public SylvanOfferingEffect1 copy() {
        return new SylvanOfferingEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetOpponent(true);
            target.choose(Outcome.Sacrifice, source.getControllerId(), source.getSourceId(), source, game);
            Player opponent = game.getPlayer(target.getFirstTarget());
            if (opponent != null) {
                int xValue = source.getManaCostsToPay().getX();
                Effect effect = new CreateTokenTargetEffect(new SylvanOfferingTreefolkToken(xValue));
                effect.setTargetPointer(new FixedTarget(controller.getId()));
                effect.apply(game, source);
                effect.setTargetPointer(new FixedTarget(opponent.getId()));
                effect.apply(game, source);
                return true;
            }
        }
        return false;
    }
}

class SylvanOfferingEffect2 extends OneShotEffect {

    SylvanOfferingEffect2() {
        super(Outcome.Sacrifice);
        this.staticText = "<br>Choose an opponent. You and that player each create X 1/1 green Elf Warrior creature tokens";
    }

    SylvanOfferingEffect2(final SylvanOfferingEffect2 effect) {
        super(effect);
    }

    @Override
    public SylvanOfferingEffect2 copy() {
        return new SylvanOfferingEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetOpponent(true);
            target.choose(Outcome.Sacrifice, source.getControllerId(), source.getSourceId(), source, game);
            Player opponent = game.getPlayer(target.getFirstTarget());
            if (opponent != null) {
                int xValue = source.getManaCostsToPay().getX();
                Effect effect = new CreateTokenTargetEffect(new ElfWarriorToken(), xValue);
                effect.setTargetPointer(new FixedTarget(controller.getId()));
                effect.apply(game, source);
                effect.setTargetPointer(new FixedTarget(opponent.getId()));
                effect.apply(game, source);
                return true;
            }
        }
        return false;
    }
}

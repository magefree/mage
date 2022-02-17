package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ChainersTormentNightmareToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChainersTorment extends CardImpl {

    public ChainersTorment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II — Chainer's Torment deals 2 damage to each opponent and you gain 2 life.
        Effects effects = new Effects();
        effects.add(new DamagePlayersEffect(2, TargetController.OPPONENT));
        effects.add(new GainLifeEffect(2).setText("and you gain 2 life"));
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, effects);

        // III — Create an X/X black Nightmare Horror creature token, where X is half your life total, rounded up. It deals X damage to you.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ChainersTormentEffect());
        this.addAbility(sagaAbility);
    }

    private ChainersTorment(final ChainersTorment card) {
        super(card);
    }

    @Override
    public ChainersTorment copy() {
        return new ChainersTorment(this);
    }
}

class ChainersTormentEffect extends OneShotEffect {

    ChainersTormentEffect() {
        super(Outcome.Benefit);
        this.staticText = "Create an X/X black Nightmare Horror creature token, where X is half your life total, rounded up. It deals X damage to you";
    }

    ChainersTormentEffect(final ChainersTormentEffect effect) {
        super(effect);
    }

    @Override
    public ChainersTormentEffect copy() {
        return new ChainersTormentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int xValue = (int) Math.ceil((1.0 * Math.max(0, player.getLife())) / 2);
        CreateTokenEffect effect = new CreateTokenEffect(new ChainersTormentNightmareToken(xValue));
        if (effect.apply(game, source)) {
            for (UUID tokenId : effect.getLastAddedTokenIds()) {
                Permanent token = game.getPermanentOrLKIBattlefield(tokenId);
                if (token != null) {
                    player.damage(xValue, tokenId, source, game);
                }
            }
        }
        return true;
    }
}

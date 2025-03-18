package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.RedWarriorToken;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author balazskristof
 */
public class MobilizeAbility extends AttacksTriggeredAbility {

    public MobilizeAbility(int count) {
        super(new MobilizeEffect(count), false, "Mobilize " + count + " <i>(Whenever this creature attacks, create "
            + (count == 1 ? "a" : CardUtil.numberToText(count)) + " tapped and attacking 1/1 red Warrior creature "
            + (count == 1 ? "token" : "tokens") + ". Sacrifice " + (count == 1 ? "it" : "them")
            + " at the beginning of the next end step.)");
    }

    protected MobilizeAbility(final MobilizeAbility ability) {
        super(ability);
    }

    @Override
    public MobilizeAbility copy() {
        return new MobilizeAbility(this);
    }
}

class MobilizeEffect extends OneShotEffect {

    private final int count;

    MobilizeEffect(int count) {
        super(Outcome.Benefit);
        this.count = count;
    }

    private MobilizeEffect(final MobilizeEffect effect) {
        super(effect);
        this.count = effect.count;
    }

    @Override
    public MobilizeEffect copy() {
        return new MobilizeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        CreateTokenEffect effect = new CreateTokenEffect(new RedWarriorToken(), this.count, true, true);
        effect.apply(game, source);
        effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GermToken;
import mage.players.Player;

public class LivingWeaponAbility extends EntersBattlefieldTriggeredAbility {

    public LivingWeaponAbility() {
        super(new LivingWeaponEffect());
    }

    public LivingWeaponAbility(final LivingWeaponAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "Living weapon <i>(When this Equipment enters the battlefield, create a 0/0 black Germ creature token, then attach this to it.)</i>";
    }

    @Override
    public EntersBattlefieldTriggeredAbility copy() {
        return new LivingWeaponAbility(this);
    }
}

class LivingWeaponEffect extends CreateTokenEffect {

    LivingWeaponEffect() {
        super(new GermToken());
    }

    LivingWeaponEffect(final LivingWeaponEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (super.apply(game, source)) {
                Permanent p = game.getPermanent(this.getLastAddedTokenId());
                if (p != null) {
                    p.addAttachment(source.getSourceId(), game);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public LivingWeaponEffect copy() {
        return new LivingWeaponEffect(this);
    }
}

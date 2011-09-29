package mage.abilities.keyword;

import mage.Constants;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

public class LivingWeaponAbility extends EntersBattlefieldTriggeredAbility {
    public LivingWeaponAbility() {
        super(new LivingWeaponEffect());
    }

     public LivingWeaponAbility(final LivingWeaponAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "Living weapon";
    }

    @Override
    public EntersBattlefieldTriggeredAbility copy() {
        return new LivingWeaponAbility(this);
    }
}

class LivingWeaponEffect extends OneShotEffect<LivingWeaponEffect> {
    LivingWeaponEffect() {
        super(Constants.Outcome.PutCreatureInPlay);
    }

    LivingWeaponEffect(final LivingWeaponEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        GermToken token = new GermToken();
        token.putOntoBattlefield(1, game, source.getId(), source.getControllerId());
        Permanent p = game.getPermanent(token.getLastAddedToken());
        if (p != null) {
             p.addAttachment(source.getSourceId(), game);
        }
        return false;
    }

    @Override
    public LivingWeaponEffect copy() {
        return new LivingWeaponEffect(this);
    }
}

class GermToken extends Token {
    public GermToken() {
        super("Germ", "a 0/0 black Germ creature token");
        cardType.add(Constants.CardType.CREATURE);
		color = ObjectColor.BLACK;
		subtype.add("Germ");
		power = new MageInt(0);
		toughness = new MageInt(0);
    }
}
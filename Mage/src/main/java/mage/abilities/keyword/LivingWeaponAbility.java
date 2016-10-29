package mage.abilities.keyword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
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

class GermToken extends Token {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("C14", "MBS", "MM2"));
    }

    public GermToken() {
        super("Germ", "a 0/0 black Germ creature token");
        availableImageSetCodes = tokenImageSets;
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add("Germ");
        power = new MageInt(0);
        toughness = new MageInt(0);
    }
}

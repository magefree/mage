package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class SmokeBlessingToken extends TokenImpl {

    public SmokeBlessingToken() {
        super(
                "Smoke Blessing", "red Aura enchantment token named Smoke Blessing " +
                        "attached to that creature. Those tokens have enchant creature and " +
                        "\"When enchanted creature dies, it deals 1 damage to its controller and you create a Treasure token.\""
        );
        cardType.add(CardType.ENCHANTMENT);
        color.setRed(true);
        subtype.add(SubType.AURA);

        TargetPermanent auraTarget = new TargetPermanent();
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        ability.addTarget(auraTarget);
        ability.addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(ability);

        this.addAbility(new DiesAttachedTriggeredAbility(new SmokeBlessingTokenEffect(), "enchanted creature"));

        availableImageSetCodes = Arrays.asList("NEC");
    }

    public SmokeBlessingToken(final SmokeBlessingToken token) {
        super(token);
    }

    public SmokeBlessingToken copy() {
        return new SmokeBlessingToken(this);
    }
}

class SmokeBlessingTokenEffect extends OneShotEffect {

    SmokeBlessingTokenEffect() {
        super(Outcome.Benefit);
        staticText = "it deals 1 damage to its controller and you create a Treasure token";
    }

    private SmokeBlessingTokenEffect(final SmokeBlessingTokenEffect effect) {
        super(effect);
    }

    @Override
    public SmokeBlessingTokenEffect copy() {
        return new SmokeBlessingTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("attachedTo");
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                player.damage(1, permanent.getId(), source, game);
            }
        }
        new TreasureToken().putOntoBattlefield(1, game, source);
        return true;
    }
}

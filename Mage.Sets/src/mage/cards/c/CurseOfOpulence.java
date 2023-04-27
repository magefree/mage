package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EnchantedPlayerAttackedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GoldToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.constants.Zone;

/**
 * @author Saga
 */
public final class CurseOfOpulence extends CardImpl {

    public CurseOfOpulence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");
        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // Whenever enchanted player is attacked, create a colorless artifact token named Gold.
        // It has "sacrifice this artifact: Add one mana of any color." Each opponent attacking that player does the same.
        this.addAbility(new EnchantedPlayerAttackedTriggeredAbility(new CurseOfOpulenceEffect()));
    }

    private CurseOfOpulence(final CurseOfOpulence card) {
        super(card);
    }

    @Override
    public CurseOfOpulence copy() {
        return new CurseOfOpulence(this);
    }
}

class CurseOfOpulenceEffect extends OneShotEffect {

    CurseOfOpulenceEffect() {
        super(Outcome.Benefit);
        this.staticText = "create a Gold token. Each opponent attacking that player does the same.";
    }

    private CurseOfOpulenceEffect(final CurseOfOpulenceEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfOpulenceEffect copy() {
        return new CurseOfOpulenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // In the case that the enchantment is blinked
        Permanent enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (enchantment == null) {
            // It was not blinked, use the standard method
            enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        }
        if (enchantment == null) {
            return false;
        }
        Player enchantedPlayer = game.getPlayer(enchantment.getAttachedTo());
        if (enchantedPlayer == null) {
            return false;
        }
        Set<UUID> players = new HashSet<>();
        for (UUID attacker : game.getCombat().getAttackers()) {
            UUID defender = game.getCombat().getDefenderId(attacker);
            if (defender.equals(enchantedPlayer.getId())
                    && game.getPlayer(source.getControllerId()).hasOpponent(game.getPermanent(attacker).getControllerId(), game)) {
                players.add(game.getPermanent(attacker).getControllerId());
            }
        }
        players.add(source.getControllerId());
        for (UUID player : players) {
            game.getPlayer(player);
            Effect effect = new CreateTokenTargetEffect(new GoldToken());
            effect.setTargetPointer(new FixedTarget(player));
            effect.apply(game, source);
        }
        return true;
    }
}

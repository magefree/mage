package mage.cards.c;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EnchantedPlayerAttackedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Saga
 */
public final class CurseOfVerbosity extends CardImpl {

    public CurseOfVerbosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Whenever enchanted player is attacked, draw a card. Each opponent attacking that player does the same.
        this.addAbility(new EnchantedPlayerAttackedTriggeredAbility(new CurseOfVerbosityEffect()));
    }

    private CurseOfVerbosity(final CurseOfVerbosity card) {
        super(card);
    }

    @Override
    public CurseOfVerbosity copy() {
        return new CurseOfVerbosity(this);
    }
}

class CurseOfVerbosityEffect extends OneShotEffect {

    CurseOfVerbosityEffect() {
        super(Outcome.Benefit);
        this.staticText = "you draw a card. Each opponent attacking that player does the same.";
    }

    CurseOfVerbosityEffect(final CurseOfVerbosityEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfVerbosityEffect copy() {
        return new CurseOfVerbosityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // In the case that the enchantment is blinked
        Permanent enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (enchantment == null) {
            // It was not blinked, use the standard method
            enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        }
        if (enchantment != null) {
            Player enchantedPlayer = game.getPlayer(enchantment.getAttachedTo());
            if (enchantedPlayer != null) {
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
                    Effect effect = new DrawCardTargetEffect(1);
                    effect.setTargetPointer(new FixedTarget(player));
                    effect.apply(game, source);
                }
            }
            return true;

        }
        return false;
    }
}

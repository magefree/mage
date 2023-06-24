package mage.cards.c;

import java.util.HashSet;
import java.util.Set;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EnchantedPlayerAttackedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.constants.Zone;
import mage.players.Player;

/**
 *
 * @author Saga
 */
public final class CurseOfVitality extends CardImpl {

    public CurseOfVitality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // Whenever enchanted player is attacked, you gain 2 life. Each opponent attacking that player does the same.
        this.addAbility(new EnchantedPlayerAttackedTriggeredAbility(new CurseOfVitalityEffect()));
    }

    private CurseOfVitality(final CurseOfVitality card) {
        super(card);
    }

    @Override
    public CurseOfVitality copy() {
        return new CurseOfVitality(this);
    }
}

class CurseOfVitalityEffect extends OneShotEffect {

    CurseOfVitalityEffect() {
        super(Outcome.Benefit);
        this.staticText = "gain 2 life. Each opponent attacking that player does the same.";
    }

    CurseOfVitalityEffect(final CurseOfVitalityEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfVitalityEffect copy() {
        return new CurseOfVitalityEffect(this);
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
                    Effect effect = new GainLifeTargetEffect(2);
                    effect.setTargetPointer(new FixedTarget(player));
                    effect.apply(game, source);
                }
            }
            return true;

        }
        return false;
    }
}

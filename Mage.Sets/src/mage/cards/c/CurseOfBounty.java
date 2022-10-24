package mage.cards.c;

import java.util.HashSet;
import java.util.Set;
import mage.abilities.Ability;
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
import mage.abilities.common.EnchantedPlayerAttackedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.players.Player;

/**
 *
 * @author Saga
 */
public final class CurseOfBounty extends CardImpl {

    public CurseOfBounty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // Whenever enchanted player is attacked, untap all nonland permanents you control.
        // Each opponent attacking that player untaps all nonland permanents they control.
        this.addAbility(new EnchantedPlayerAttackedTriggeredAbility(new CurseOfBountyEffect()));
    }

    private CurseOfBounty(final CurseOfBounty card) {
        super(card);
    }

    @Override
    public CurseOfBounty copy() {
        return new CurseOfBounty(this);
    }
}

class CurseOfBountyEffect extends OneShotEffect {

    CurseOfBountyEffect() {
        super(Outcome.Benefit);
        this.staticText = "untap all nonland permanents you control. Each opponent attacking that player does the same.";
    }

    CurseOfBountyEffect(final CurseOfBountyEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfBountyEffect copy() {
        return new CurseOfBountyEffect(this);
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
                    Effect effect = new UntapAllNonlandsTargetEffect();
                    effect.setTargetPointer(new FixedTarget(player));
                    effect.apply(game, source);
                }
            }
            return true;

        }
        return false;
    }
}

class UntapAllNonlandsTargetEffect extends OneShotEffect {

    public UntapAllNonlandsTargetEffect() {
        super(Outcome.Untap);
    }

    public UntapAllNonlandsTargetEffect(final UntapAllNonlandsTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            for (Permanent nonland : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_NON_LAND, player.getId(), game)) {
                nonland.untap(game);
            }
            return true;
        }
        return false;
    }

    @Override
    public UntapAllNonlandsTargetEffect copy() {
        return new UntapAllNonlandsTargetEffect(this);
    }

}

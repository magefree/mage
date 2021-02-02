package mage.cards.p;

import java.util.List;
import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedAttachedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class PretendersClaim extends CardImpl {

    public PretendersClaim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted creature becomes blocked, tap all lands defending player controls.
        this.addAbility(new BecomesBlockedAttachedTriggeredAbility(new TapDefendingPlayerLandEffect(), false));

    }

    private PretendersClaim(final PretendersClaim card) {
        super(card);
    }

    @Override
    public PretendersClaim copy() {
        return new PretendersClaim(this);
    }
}

class TapDefendingPlayerLandEffect extends OneShotEffect {

    public TapDefendingPlayerLandEffect() {
        super(Outcome.Tap);
        staticText = "tap all lands defending player controls";
    }

    public TapDefendingPlayerLandEffect(final TapDefendingPlayerLandEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent aura = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (aura != null
                && aura.getAttachedTo() != null) {
            Player defendingPlayer = game.getPlayer(game.getCombat().getDefendingPlayerId(aura.getAttachedTo(), game));
            if (defendingPlayer != null) {
                List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, defendingPlayer.getId(), game);
                for (Permanent land : permanents) {
                    land.tap(source, game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public TapDefendingPlayerLandEffect copy() {
        return new TapDefendingPlayerLandEffect(this);
    }
}

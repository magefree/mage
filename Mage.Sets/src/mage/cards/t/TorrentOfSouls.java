
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public final class TorrentOfSouls extends CardImpl {

    public TorrentOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B/R}");

        // Return up to one target creature card from your graveyard to the battlefield if {B} was spent to cast Torrent of Souls. Creatures target player controls get +2/+0 and gain haste until end of turn if {R} was spent to cast Torrent of Souls.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ReturnFromGraveyardToBattlefieldTargetEffect(),
                ManaWasSpentCondition.BLACK, "Return up to one target creature card from your graveyard to the battlefield if {B} was spent to cast this spell"));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new TorrentOfSoulsEffect(),
                ManaWasSpentCondition.RED, "Creatures target player controls get +2/+0 and gain haste until end of turn if {R} was spent to cast this spell"));

        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getSpellAbility().addTarget(new TargetPlayer());

        this.getSpellAbility().addEffect(new InfoEffect("<i>(Do both if {B}{R} was spent.)</i>"));

    }

    private TorrentOfSouls(final TorrentOfSouls card) {
        super(card);
    }

    @Override
    public TorrentOfSouls copy() {
        return new TorrentOfSouls(this);
    }
}

class TorrentOfSoulsEffect extends OneShotEffect {

    public TorrentOfSoulsEffect() {
        super(Outcome.BoostCreature);
    }

    public TorrentOfSoulsEffect(final TorrentOfSoulsEffect effect) {
        super(effect);
    }

    @Override
    public TorrentOfSoulsEffect copy() {
        return new TorrentOfSoulsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetedPlayer = game.getPlayer(source.getTargets().get(1).getFirstTarget());
        if (targetedPlayer != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate(targetedPlayer.getId()));
            ContinuousEffect boostEffect = new BoostAllEffect(2, 0, Duration.EndOfTurn, filter, true);
            ContinuousEffect gainAbilityEffect = new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.EndOfTurn, filter);
            game.addEffect(boostEffect, source);
            game.addEffect(gainAbilityEffect, source);
            return true;
        }
        return false;
    }
}

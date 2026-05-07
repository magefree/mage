package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.BountyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class OpenSeason extends CardImpl {

    public OpenSeason(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // When {this} enters, for each opponent, put a bounty counter on target creature that player controls
        Effect effect = new AddCountersTargetEffect(CounterType.BOUNTY.createInstance());
        effect.setText("for each opponent, put a bounty counter on target creature that player controls");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect);
        ability.addTarget(new TargetCreaturePermanent());
        ability.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
        this.addAbility(ability);

        // Creatures your opponent control with bounty counters on them can't activate abilities
        this.addAbility(new SimpleStaticAbility(new OpenSeasonRestrictionEffect()));

        // Bounty - Whenever a creature an opponent controls with a bounty counter on it dies, that creature's controller loses 2 life. Each other player gains 2 life.
        ability = new BountyAbility(
                new LoseLifeTargetEffect(2).withTargetDescription("that creature's controller"),
                false, SetTargetPointer.PLAYER);
        ability.addEffect(new OpenSeasonEffect());
        this.addAbility(ability);

    }

    private OpenSeason(final OpenSeason card) {
        super(card);
    }

    @Override
    public OpenSeason copy() {
        return new OpenSeason(this);
    }
}

class OpenSeasonRestrictionEffect extends RestrictionEffect {

    OpenSeasonRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Creatures your opponent control with bounty counters on them can't activate abilities";
    }

    private OpenSeasonRestrictionEffect(final OpenSeasonRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isCreature(game)
                && permanent.getCounters(game).getCount(CounterType.BOUNTY) > 0
                && game.getOpponents(source.getControllerId()).contains(permanent.getControllerId());
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public OpenSeasonRestrictionEffect copy() {
        return new OpenSeasonRestrictionEffect(this);
    }

}

class OpenSeasonEffect extends OneShotEffect {

    OpenSeasonEffect() {
        super(Outcome.GainLife);
        staticText = "Each other player gains 2 life";
    }

    private OpenSeasonEffect(final OpenSeasonEffect effect) {
        super(effect);
    }

    @Override
    public OpenSeasonEffect copy() {
        return new OpenSeasonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            if (playerId.equals(getTargetPointer().getFirst(game, source))) {
                continue;
            }
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.gainLife(2, game, source);
            }
        }
        return true;
    }

}

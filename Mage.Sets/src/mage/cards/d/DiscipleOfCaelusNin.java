package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DiscipleOfCaelusNin extends CardImpl {

    public DiscipleOfCaelusNin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Disciple of Caelus Nin enters the battlefield, starting with you, each player chooses up to five permanents they control. All permanents other than Disciple of Caelus Nin that weren't chosen this way phase out.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscipleOfCaelusNinFirstEffect()));

        // Permanents can't phase in.
        this.addAbility(new SimpleStaticAbility(new DiscipleOfCaelusNinSecondEffect()));
    }

    private DiscipleOfCaelusNin(final DiscipleOfCaelusNin card) {
        super(card);
    }

    @Override
    public DiscipleOfCaelusNin copy() {
        return new DiscipleOfCaelusNin(this);
    }
}

class DiscipleOfCaelusNinFirstEffect extends OneShotEffect {

    DiscipleOfCaelusNinFirstEffect() {
        super(Outcome.Benefit);
        staticText = "starting with you, each player chooses up to five permanents they control. " +
                "All permanents other than {this} that weren't chosen this way phase out";
    }

    private DiscipleOfCaelusNinFirstEffect(final DiscipleOfCaelusNinFirstEffect effect) {
        super(effect);
    }

    @Override
    public DiscipleOfCaelusNinFirstEffect copy() {
        return new DiscipleOfCaelusNinFirstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> toKeep = new HashSet<>();
        if (source.getSourcePermanentIfItStillExists(game) != null) {
            toKeep.add(source.getSourceId());
        }
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(source.getControllerId());
            if (player == null) {
                continue;
            }
            TargetPermanent target = new TargetPermanent(0, 5, StaticFilters.FILTER_CONTROLLED_PERMANENT, true);
            player.choose(outcome, target, source, game);
            toKeep.addAll(target.getTargets());
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            if (!toKeep.contains(permanent.getId())) {
                permanent.phaseOut(game);
            }
        }
        return true;
    }
}

class DiscipleOfCaelusNinSecondEffect extends ContinuousRuleModifyingEffectImpl {

    DiscipleOfCaelusNinSecondEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "permanents can't phase in";
    }

    private DiscipleOfCaelusNinSecondEffect(final DiscipleOfCaelusNinSecondEffect effect) {
        super(effect);
    }

    @Override
    public DiscipleOfCaelusNinSecondEffect copy() {
        return new DiscipleOfCaelusNinSecondEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PHASE_IN;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
}

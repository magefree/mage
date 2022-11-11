package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TriarchStalker extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures attacking the last chosen player");

    static {
        filter.add(TriarchStalkerPredicate.instance);
    }

    public TriarchStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.NECRON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Targeting Relay — At the beginning of combat on your turn, choose an opponent.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new TriarchStalkerEffect(), TargetController.YOU, false
        ).withFlavorWord("Targeting Relay"));

        // Creatures attacking the last chosen player have menace.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(new MenaceAbility(false), Duration.WhileOnBattlefield, filter)));
    }

    private TriarchStalker(final TriarchStalker card) {
        super(card);
    }

    @Override
    public TriarchStalker copy() {
        return new TriarchStalker(this);
    }
}

enum TriarchStalkerPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        UUID playerId = (UUID) game.getState().getValue(input.getSourceId() + "_" + game.getState().getZoneChangeCounter(input.getSourceId()) + "_chosenOpponent");
        return playerId != null && playerId.equals(game.getCombat().getDefendingPlayerId(input.getObject().getId(), game));
    }
}

class TriarchStalkerEffect extends OneShotEffect {

    TriarchStalkerEffect() {
        super(Outcome.Benefit);
        staticText = "choose an opponent";
    }

    private TriarchStalkerEffect(final TriarchStalkerEffect effect) {
        super(effect);
    }

    @Override
    public TriarchStalkerEffect copy() {
        return new TriarchStalkerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (player == null || permanent == null) {
            return false;
        }
        TargetOpponent target = new TargetOpponent();
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Player chosenPlayer = game.getPlayer(target.getFirstTarget());
        if (chosenPlayer == null) {
            return false;
        }
        game.informPlayers(permanent.getName() + ": " + player.getLogName() + " has chosen " + chosenPlayer.getLogName());
        game.getState().setValue(permanent.getId() + "_" + permanent.getZoneChangeCounter(game) + "_chosenOpponent", chosenPlayer.getId());
        permanent.addInfo("chosen opponent", CardUtil.addToolTipMarkTags("Chosen player: " + chosenPlayer.getLogName()), game);
        return true;
    }
}

package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
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
 * @author TheElk801
 */
public final class BeckoningWillOWisp extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures attacking the last chosen player");

    static {
        filter.add(BeckoningWillOWispPredicate.instance);
    }

    public BeckoningWillOWisp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lure the Unwary — At the beginning of combat on your turn, choose an opponent.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new BeckoningWillOWispEffect(), TargetController.YOU, false
        ).withFlavorWord("Lure the Unwary"));

        // Creatures attacking the last chosen player get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                1, 0, Duration.WhileOnBattlefield, filter, false
        )));
    }

    private BeckoningWillOWisp(final BeckoningWillOWisp card) {
        super(card);
    }

    @Override
    public BeckoningWillOWisp copy() {
        return new BeckoningWillOWisp(this);
    }
}

enum BeckoningWillOWispPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        UUID playerId = (UUID) game.getState().getValue(input.getSourceId() + "_" + game.getState().getZoneChangeCounter(input.getSourceId()) + "_chosenOpponent");
        return playerId != null && playerId.equals(game.getCombat().getDefendingPlayerId(input.getObject().getId(), game));
    }
}

class BeckoningWillOWispEffect extends OneShotEffect {

    BeckoningWillOWispEffect() {
        super(Outcome.Benefit);
        staticText = "choose an opponent";
    }

    private BeckoningWillOWispEffect(final BeckoningWillOWispEffect effect) {
        super(effect);
    }

    @Override
    public BeckoningWillOWispEffect copy() {
        return new BeckoningWillOWispEffect(this);
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

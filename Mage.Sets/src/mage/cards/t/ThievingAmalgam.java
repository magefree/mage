package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThievingAmalgam extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a creature you control but don't own");

    static {
        filter.add(TargetController.NOT_YOU.getOwnerPredicate());
    }

    public ThievingAmalgam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.subtype.add(SubType.APE);
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(7);

        // At the beginning of each opponent's upkeep, you manifest the top card of that player's library.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ThievingAmalgamManifestEffect(), TargetController.OPPONENT, false
        ));

        // Whenever a creature you control but don't own dies, its owner loses 2 life and you gain 2 life.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new ThievingAmalgamLifeLossEffect(), false, filter, true
        ));
    }

    private ThievingAmalgam(final ThievingAmalgam card) {
        super(card);
    }

    @Override
    public ThievingAmalgam copy() {
        return new ThievingAmalgam(this);
    }
}

class ThievingAmalgamManifestEffect extends OneShotEffect {

    ThievingAmalgamManifestEffect() {
        super(Outcome.Benefit);
        staticText = "you manifest the top card of that player's library";
    }

    private ThievingAmalgamManifestEffect(final ThievingAmalgamManifestEffect effect) {
        super(effect);
    }

    @Override
    public ThievingAmalgamManifestEffect copy() {
        return new ThievingAmalgamManifestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(game.getActivePlayerId());
        if (controller == null || targetPlayer == null) {
            return false;
        }

        return !ManifestEffect.doManifestCards(game, source, controller, targetPlayer.getLibrary().getTopCards(game, 1)).isEmpty();
    }
}

class ThievingAmalgamLifeLossEffect extends OneShotEffect {

    private static final Effect effect = new GainLifeEffect(2);

    ThievingAmalgamLifeLossEffect() {
        super(Outcome.Benefit);
        staticText = "its owner loses 2 life and you gain 2 life";
    }

    private ThievingAmalgamLifeLossEffect(final ThievingAmalgamLifeLossEffect effect) {
        super(effect);
    }

    @Override
    public ThievingAmalgamLifeLossEffect copy() {
        return new ThievingAmalgamLifeLossEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getOwnerId(getTargetPointer().getFirst(game, source)));
        if (player == null) {
            return false;
        }
        player.loseLife(2, game, source, false);
        return effect.apply(game, source);
    }
}

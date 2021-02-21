package mage.cards.t;

import java.util.Iterator;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierLifelinkToken;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class TrostaniDiscordant extends CardImpl {

    public TrostaniDiscordant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Other creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new BoostControlledEffect(
                        1, 1, Duration.WhileOnBattlefield, true
                )
        ));

        // When Trostani Discordant enters the battlefield, create two 1/1 white Soldier creature tokens with lifelink.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new SoldierLifelinkToken(), 2)
        ));

        // At the beginning of your end step, each player gains control of all creatures they own.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new TrostaniDiscordantEffect(), TargetController.YOU, false
        ));
    }

    private TrostaniDiscordant(final TrostaniDiscordant card) {
        super(card);
    }

    @Override
    public TrostaniDiscordant copy() {
        return new TrostaniDiscordant(this);
    }
}

class TrostaniDiscordantEffect extends ContinuousEffectImpl {

    public TrostaniDiscordantEffect() {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.staticText = "each player gains control of all creatures they own";
    }

    public TrostaniDiscordantEffect(final TrostaniDiscordantEffect effect) {
        super(effect);
    }

    @Override
    public TrostaniDiscordantEffect copy() {
        return new TrostaniDiscordantEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        // add all creatures in range
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                FilterPermanent playerFilter = new FilterCreaturePermanent();
                playerFilter.add(new OwnerIdPredicate(playerId));
                for (Permanent permanent : game.getBattlefield().getActivePermanents(playerFilter, playerId, game)) {
                    affectedObjectList.add(new MageObjectReference(permanent, game));
                }
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
            Permanent creature = it.next().getPermanent(game);
            if (creature != null) {
                if (!creature.isControlledBy(creature.getOwnerId())) {
                    creature.changeControllerId(creature.getOwnerId(), game, source);
                }
            } else {
                it.remove();
            }
        }
        if (affectedObjectList.isEmpty()) {
            this.discard();
        }
        return true;
    }
}

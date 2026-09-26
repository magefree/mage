package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SanctumToken;
import mage.players.Player;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class ZagorkaMotherOfSanctum extends CardImpl {

    public ZagorkaMotherOfSanctum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your end step, each player may create a tapped land token named Sanctum with "{T}: Add one mana of any color." Each opponent who does can't attack you during their next turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new ZagorkaMotherOfSanctumEffect()));
    }

    private ZagorkaMotherOfSanctum(final ZagorkaMotherOfSanctum card) {
        super(card);
    }

    @Override
    public ZagorkaMotherOfSanctum copy() {
        return new ZagorkaMotherOfSanctum(this);
    }
}

class ZagorkaMotherOfSanctumEffect extends OneShotEffect {
    ZagorkaMotherOfSanctumEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player may create a tapped land token named Sanctum with \"{T}: Add one mana of any color.\". Each opponent who does can't attack you during their next turn.";
    }

    private ZagorkaMotherOfSanctumEffect(final ZagorkaMotherOfSanctumEffect effect) {
        super(effect);
    }

    @Override
    public ZagorkaMotherOfSanctumEffect copy() {
        return new ZagorkaMotherOfSanctumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
	        return false;
        }

        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null
	            && player.chooseUse(Outcome.Benefit, "Create tapped land token named Sanctum with \"{T}: Add one mana of any color.\"?", source, game)
                && new SanctumToken().putOntoBattlefield(1, game, source, player.getId(), true, false)
		        && game.getOpponents(controller.getId()).contains(playerId)
            ) {
                RestrictionEffect effect = new ZagorkaMotherOfSanctumCantAttackEffect(player.getId());
                game.addEffect(effect, source);
            }
        }

        return true;
    }
}

class ZagorkaMotherOfSanctumCantAttackEffect extends RestrictionEffect {

    private final UUID opponentId;

    public ZagorkaMotherOfSanctumCantAttackEffect(UUID opponentId) {
        super(Duration.UntilEndOfYourNextTurn);
        this.opponentId = opponentId;
        staticText = "";
    }

    private ZagorkaMotherOfSanctumCantAttackEffect(final ZagorkaMotherOfSanctumCantAttackEffect effect) {
        super(effect);
	    this.opponentId = effect.opponentId;
    }

    @Override
    public ZagorkaMotherOfSanctumCantAttackEffect copy() {
        return new ZagorkaMotherOfSanctumCantAttackEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
	    if (opponentId != null) {
            setStartingControllerAndTurnNum(game, opponentId, game.getActivePlayerId());
        } else {
            discard();
        }
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return game.isActivePlayer(opponentId);
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        UUID controllerId = source.getControllerId();
	    if (defenderId == null) {
	        return true;
	    }

        return !defenderId.equals(controllerId);
    }
}

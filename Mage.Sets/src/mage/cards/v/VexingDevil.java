package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author noxx
 */
public final class VexingDevil extends CardImpl {

    public VexingDevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.DEVIL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Vexing Devil enters the battlefield, any opponent may have it deal 4 damage to them. If a player does, sacrifice Vexing Devil.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new VexingDevilEffect(), false));
    }

    private VexingDevil(final VexingDevil card) {
        super(card);
    }

    @Override
    public VexingDevil copy() {
        return new VexingDevil(this);
    }
}

class VexingDevilEffect extends OneShotEffect {

    public VexingDevilEffect() {
        super(Outcome.Neutral);
        staticText = "any opponent may have it deal 4 damage to them. If a player does, sacrifice {this}";
    }

    private VexingDevilEffect(final VexingDevilEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            for (UUID opponentUuid : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(opponentUuid);
                if (opponent != null && opponent.chooseUse(Outcome.LoseLife, "Make " + permanent.getLogName() + " deal 4 damage to you?", source, game)) {
                    game.informPlayers(opponent.getLogName() + " has chosen to receive 4 damage from " + permanent.getLogName());
                    opponent.damage(4, permanent.getId(), source, game);
                    permanent.sacrifice(source, game);
                    return true;
                }
            }
            game.informPlayers("4 damage wasn't dealt so " + permanent.getLogName() + " won't be sacrificed.");
            return true;
        }
        return false;
    }

    @Override
    public VexingDevilEffect copy() {
        return new VexingDevilEffect(this);
    }

}

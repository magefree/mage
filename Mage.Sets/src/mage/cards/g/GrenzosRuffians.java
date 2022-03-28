package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MeleeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrenzosRuffians extends CardImpl {

    public GrenzosRuffians(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Melee
        this.addAbility(new MeleeAbility());

        // Whenever Grenzo's Ruffians deals combat damage to a opponent, it deals that much damage to each other opponent.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(new GrenzosRuffiansEffect(), false, true, true));
    }

    private GrenzosRuffians(final GrenzosRuffians card) {
        super(card);
    }

    @Override
    public GrenzosRuffians copy() {
        return new GrenzosRuffians(this);
    }
}

class GrenzosRuffiansEffect extends OneShotEffect {

    public GrenzosRuffiansEffect() {
        super(Outcome.Benefit);
        this.staticText = "it deals that much damage to each other opponent";
    }

    public GrenzosRuffiansEffect(final GrenzosRuffiansEffect effect) {
        super(effect);
    }

    @Override
    public GrenzosRuffiansEffect copy() {
        return new GrenzosRuffiansEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID damagedOpponent = this.getTargetPointer().getFirst(game, source);
        int amount = (Integer) getValue("damage");
        MageObject object = game.getObject(source);
        if (object != null && amount > 0 && damagedOpponent != null) {
            for (UUID playerId : game.getOpponents(source.getControllerId())) {
                if (!Objects.equals(playerId, damagedOpponent)) {
                    Player opponent = game.getPlayer(playerId);
                    if (opponent != null) {
                        int dealtDamage = opponent.damage(amount, source.getSourceId(), source, game);
                        game.informPlayers(object.getLogName() + " deals " + dealtDamage + " damage to " + opponent.getLogName());
                    }
                }
            }
            return true;
        }
        return false;
    }
}

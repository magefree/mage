package mage.cards.h;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
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
 * @author LevelX2
 */
public final class HydraOmnivore extends CardImpl {

    public HydraOmnivore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Whenever Hydra Omnivore deals combat damage to an opponent, it deals that much damage to each other opponent.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(new HydraOmnivoreEffect(), false, true, true));
    }

    private HydraOmnivore(final HydraOmnivore card) {
        super(card);
    }

    @Override
    public HydraOmnivore copy() {
        return new HydraOmnivore(this);
    }
}

class HydraOmnivoreEffect extends OneShotEffect {

    public HydraOmnivoreEffect() {
        super(Outcome.Benefit);
        this.staticText = "it deals that much damage to each other opponent";
    }

    public HydraOmnivoreEffect(final HydraOmnivoreEffect effect) {
        super(effect);
    }

    @Override
    public HydraOmnivoreEffect copy() {
        return new HydraOmnivoreEffect(this);
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

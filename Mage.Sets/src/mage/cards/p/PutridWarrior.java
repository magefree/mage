package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DealsDamageSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class PutridWarrior extends CardImpl {

    public PutridWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Putrid Warrior deals damage, choose one - Each player loses 1 life; or each player gains 1 life.
        Ability ability = new DealsDamageSourceTriggeredAbility(new LoseLifeAllPlayersEffect(1));
        ability.addMode(new Mode(new PutridWarriorGainLifeEffect()));
        this.addAbility(ability);
    }

    private PutridWarrior(final PutridWarrior card) {
        super(card);
    }

    @Override
    public PutridWarrior copy() {
        return new PutridWarrior(this);
    }
}

class PutridWarriorGainLifeEffect extends OneShotEffect {

    PutridWarriorGainLifeEffect() {
        super(Outcome.GainLife);
        staticText = "each player gains 1 life";
    }

    private PutridWarriorGainLifeEffect(final PutridWarriorGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public PutridWarriorGainLifeEffect copy() {
        return new PutridWarriorGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.gainLife(1, game, source);
            }
        }
        return true;
    }

}

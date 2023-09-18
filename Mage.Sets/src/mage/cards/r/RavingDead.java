
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.AttackIfAbleTargetRandomOpponentSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class RavingDead extends CardImpl {

    public RavingDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(6);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // At the beginning of combat on your turn, choose an opponent at random. Raving Dead attacks that player this combat if able.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new AttackIfAbleTargetRandomOpponentSourceEffect(), TargetController.YOU, false));
        // Whenever Raving Dead deals combat damage to a player, that player loses half their life, rounded down.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new RavingDeadDamageEffect(), false, true));
    }

    private RavingDead(final RavingDead card) {
        super(card);
    }

    @Override
    public RavingDead copy() {
        return new RavingDead(this);
    }
}

class RavingDeadDamageEffect extends OneShotEffect {

    public RavingDeadDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "that player loses half their life, rounded down";
    }

    private RavingDeadDamageEffect(final RavingDeadDamageEffect effect) {
        super(effect);
    }

    @Override
    public RavingDeadDamageEffect copy() {
        return new RavingDeadDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {
            Integer amount = player.getLife() / 2;
            if (amount > 0) {
                player.loseLife(amount, game, source, false);
            }
            return true;
        }
        return false;
    }
}

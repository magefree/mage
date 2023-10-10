
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class TroveOfTemptation extends CardImpl {

    public TroveOfTemptation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // Each opponent must attack you or a planeswalker you control with at least one creature each combat if able.
        addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TroveOfTemptationForceAttackEffect(Duration.WhileOnBattlefield)));

        // At the beginning of your end step, create a colorless Treasure artifact token with "{t}, Sacrifice this artifact: Add one mana of any color.”
        addAbility(new BeginningOfYourEndStepTriggeredAbility(new CreateTokenEffect(new TreasureToken()), false));
    }

    private TroveOfTemptation(final TroveOfTemptation card) {
        super(card);
    }

    @Override
    public TroveOfTemptation copy() {
        return new TroveOfTemptation(this);
    }
}

class TroveOfTemptationForceAttackEffect extends RequirementEffect {

    public TroveOfTemptationForceAttackEffect(Duration duration) {
        super(duration, true);
        staticText = "Each opponent must attack you or a planeswalker you control with at least one creature each combat if able";
    }

    private TroveOfTemptationForceAttackEffect(final TroveOfTemptationForceAttackEffect effect) {
        super(effect);
    }

    @Override
    public TroveOfTemptationForceAttackEffect copy() {
        return new TroveOfTemptationForceAttackEffect(this);
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null && controller.hasOpponent(game.getActivePlayerId(), game);
    }

    @Override
    public UUID playerMustBeAttackedIfAble(Ability source, Game game) {
        return source.getControllerId();
    }

}

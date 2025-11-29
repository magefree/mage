package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ControllerLifeCount;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnduringAngel extends TransformingDoubleFacedCard {

    public EnduringAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ANGEL}, "{2}{W}{W}{W}",
                "Angelic Enforcer",
                new SuperType[]{}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ANGEL}, "W"
        );

        // Enduring Angel
        this.getLeftHalfCard().setPT(3, 3);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Double strike
        this.getLeftHalfCard().addAbility(DoubleStrikeAbility.getInstance());

        // You have hexproof.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityControllerEffect(HexproofAbility.getInstance())));

        // If your life total would be reduced to 0 or less, instead transform Enduring Angel and your life total becomes 3. Then if Enduring Angel didn't transform this way, you lose the game.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new EnduringAngelEffect()));

        // Angelic Enforcer
        this.getRightHalfCard().setPT(0, 0);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // You have hexproof.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityControllerEffect(HexproofAbility.getInstance())));

        // Angelic Enforcer's power and toughness are each equal to your life total.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(
                ControllerLifeCount.instance
        ).setText("{this}'s power and toughness are each equal to your life total")));

        // Whenever Angelic Enforcer attacks, double your life total.
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(new GainLifeEffect(
                ControllerLifeCount.instance
        ).setText("double your life total")));
    }

    private EnduringAngel(final EnduringAngel card) {
        super(card);
    }

    @Override
    public EnduringAngel copy() {
        return new EnduringAngel(this);
    }
}

class EnduringAngelEffect extends ReplacementEffectImpl {

    EnduringAngelEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if your life total would be reduced to 0 or less, instead transform {this} " +
                "and your life total becomes 3. Then if {this} didn't transform this way, you lose the game";
    }

    private EnduringAngelEffect(final EnduringAngelEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null || player.getLife() - event.getAmount() > 0) {
            return false;
        }
        boolean transformed = permanent.transform(source, game);
        if (player.getLife() > 3) {
            event.setAmount(player.getLife() - 3);
        } else if (player.getLife() < 3) {
            event.setAmount(0);
            player.setLife(3, game, source);
        } else {
            event.setAmount(0);
        }
        if (!transformed) {
            player.lost(game);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOSE_LIFE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }

    @Override
    public EnduringAngelEffect copy() {
        return new EnduringAngelEffect(this);
    }
}

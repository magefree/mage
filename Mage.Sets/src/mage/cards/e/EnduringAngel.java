package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnduringAngel extends CardImpl {

    public EnduringAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.a.AngelicEnforcer.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // You have hexproof.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControllerEffect(HexproofAbility.getInstance())));

        // If your life total would be reduced to 0 or less, instead transform Enduring Angel and your life total becomes 3. Then if Enduring Angel didn't transform this way, you lose the game.
        this.addAbility(new TransformAbility());
        this.addAbility(new SimpleStaticAbility(new EnduringAngelEffect()));
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

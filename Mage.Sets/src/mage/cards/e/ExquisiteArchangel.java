
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public final class ExquisiteArchangel extends CardImpl {

    public ExquisiteArchangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If you would lose the game, instead exile Exquisite Archangel and your life total becomes equal to your starting life total.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ExquisiteArchangelEffect()));

    }

    private ExquisiteArchangel(final ExquisiteArchangel card) {
        super(card);
    }

    @Override
    public ExquisiteArchangel copy() {
        return new ExquisiteArchangel(this);
    }
}

class ExquisiteArchangelEffect extends ReplacementEffectImpl {

    public ExquisiteArchangelEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would lose the game, instead exile {this} and your life total becomes equal to your starting life total";
    }

    private ExquisiteArchangelEffect(final ExquisiteArchangelEffect effect) {
        super(effect);
    }

    @Override
    public ExquisiteArchangelEffect copy() {
        return new ExquisiteArchangelEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (player != null && sourcePermanent != null) {
            new ExileSourceEffect().apply(game, source);
            player.setLife(game.getStartingLife(), game, source);
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOSES;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }

}

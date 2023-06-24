
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author TheElk801
 */
public final class SqueeTheImmortal extends CardImpl {

    public SqueeTheImmortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // You may cast Squee, the Immortal from your graveyard or from exile.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SqueePlayEffect()));
    }

    private SqueeTheImmortal(final SqueeTheImmortal card) {
        super(card);
    }

    @Override
    public SqueeTheImmortal copy() {
        return new SqueeTheImmortal(this);
    }
}

class SqueePlayEffect extends AsThoughEffectImpl {

    public SqueePlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may cast {this} from your graveyard or from exile";
    }

    public SqueePlayEffect(final SqueePlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SqueePlayEffect copy() {
        return new SqueePlayEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(source.getSourceId())) {
            Card card = game.getCard(source.getSourceId());
            if (card != null && card.isOwnedBy(affectedControllerId)
                    && (game.getState().getZone(source.getSourceId()) == Zone.EXILED
                    || game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD)) {
                return true;
            }
        }
        return false;
    }
}

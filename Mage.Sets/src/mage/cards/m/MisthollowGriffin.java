
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth
 */
public final class MisthollowGriffin extends CardImpl {

    public MisthollowGriffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.subtype.add(SubType.GRIFFIN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        // You may cast Misthollow Griffin from exile.
        this.addAbility(new SimpleStaticAbility(Zone.EXILED, new MisthollowGriffinPlayEffect()));
    }

    private MisthollowGriffin(final MisthollowGriffin card) {
        super(card);
    }

    @Override
    public MisthollowGriffin copy() {
        return new MisthollowGriffin(this);
    }
}

class MisthollowGriffinPlayEffect extends AsThoughEffectImpl {

    public MisthollowGriffinPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may cast {this} from exile";
    }

    private MisthollowGriffinPlayEffect(final MisthollowGriffinPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MisthollowGriffinPlayEffect copy() {
        return new MisthollowGriffinPlayEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(source.getSourceId())) {
            Card card = game.getCard(source.getSourceId());
            if (card != null && card.isOwnedBy(affectedControllerId)
                    && game.getState().getZone(source.getSourceId()) == Zone.EXILED) {
                return true;
            }
        }
        return false;
    }
}

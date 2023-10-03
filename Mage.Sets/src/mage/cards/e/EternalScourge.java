package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class EternalScourge extends CardImpl {

    public EternalScourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // You may cast Eternal Scourge from exile.
        this.addAbility(new SimpleStaticAbility(Zone.EXILED, new EternalScourgePlayEffect()));

        // When Eternal Scourge becomes the target of a spell or ability an opponent controls, exile Eternal Scourge.
        this.addAbility(new BecomesTargetSourceTriggeredAbility(new ExileSourceEffect(),
                StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS));
    }

    private EternalScourge(final EternalScourge card) {
        super(card);
    }

    @Override
    public EternalScourge copy() {
        return new EternalScourge(this);
    }
}

class EternalScourgePlayEffect extends AsThoughEffectImpl {

    public EternalScourgePlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may cast {this} from exile";
    }

    private EternalScourgePlayEffect(final EternalScourgePlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public EternalScourgePlayEffect copy() {
        return new EternalScourgePlayEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(source.getSourceId())) {
            Card card = game.getCard(source.getSourceId());
            if (card != null && card.isOwnedBy(affectedControllerId) && game.getState().getZone(source.getSourceId()) == Zone.EXILED) {
                return true;
            }
        }
        return false;
    }
}

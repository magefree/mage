
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.watchers.common.PlayerGainedLifeWatcher;

/**
 *
 * @author LevelX2
 */
public final class OathswornVampire extends CardImpl {

    public OathswornVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Oathsworn Vampire enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // You may cast Oathsworn Vampire from your graveyard if you gained life this turn.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new OathswornVampirePlayEffect()), new PlayerGainedLifeWatcher());
    }

    public OathswornVampire(final OathswornVampire card) {
        super(card);
    }

    @Override
    public OathswornVampire copy() {
        return new OathswornVampire(this);
    }
}

class OathswornVampirePlayEffect extends AsThoughEffectImpl {

    private Condition condition;

    public OathswornVampirePlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may cast {this} from your graveyard if you gained life this turn";
        condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 0);
    }

    public OathswornVampirePlayEffect(final OathswornVampirePlayEffect effect) {
        super(effect);
        this.condition = effect.condition;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public OathswornVampirePlayEffect copy() {
        return new OathswornVampirePlayEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(source.getSourceId()) && source.isControlledBy(affectedControllerId)) {
            Card card = game.getCard(source.getSourceId());
            if (card != null && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
                return condition.apply(game, source);
            }
        }
        return false;
    }

}

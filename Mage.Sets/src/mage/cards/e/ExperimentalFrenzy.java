package mage.cards.e;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class ExperimentalFrenzy extends CardImpl {

    public ExperimentalFrenzy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, new ExperimentalFrenzyTopCardEffect()
        ));

        // You may play the top card of your library.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, new PlayTheTopCardEffect()
        ));

        // You can't play cards from your hand.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, new ExperimentalFrenzyRestrictionEffect()
        ));

        // {3}{R}: Destroy Experimental Frenzy.
        this.addAbility(new SimpleActivatedAbility(
                new DestroySourceEffect(), new ManaCostsImpl("{3}{R}")
        ));
    }

    public ExperimentalFrenzy(final ExperimentalFrenzy card) {
        super(card);
    }

    @Override
    public ExperimentalFrenzy copy() {
        return new ExperimentalFrenzy(this);
    }
}

class ExperimentalFrenzyTopCardEffect extends ContinuousEffectImpl {

    public ExperimentalFrenzyTopCardEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "You may look at the top card of your library any time.";
    }

    public ExperimentalFrenzyTopCardEffect(final ExperimentalFrenzyTopCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return true;
        }
        Card topCard = controller.getLibrary().getFromTop(game);
        if (topCard == null) {
            return true;
        }
        MageObject obj = source.getSourceObject(game);
        if (obj == null) {
            return true;
        }
        controller.lookAtCards("Top card of " + obj.getIdName() + " controller's library", topCard, game);
        return true;
    }

    @Override
    public ExperimentalFrenzyTopCardEffect copy() {
        return new ExperimentalFrenzyTopCardEffect(this);
    }
}

class ExperimentalFrenzyRestrictionEffect extends ContinuousRuleModifyingEffectImpl {

    public ExperimentalFrenzyRestrictionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "You can't play cards from your hand";
    }

    public ExperimentalFrenzyRestrictionEffect(final ExperimentalFrenzyRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public ExperimentalFrenzyRestrictionEffect copy() {
        return new ExperimentalFrenzyRestrictionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLAY_LAND
                || event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId())
                && game.getState().getZone(event.getSourceId()) == Zone.HAND;
    }
}

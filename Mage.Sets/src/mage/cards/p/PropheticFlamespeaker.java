
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class PropheticFlamespeaker extends CardImpl {

    public PropheticFlamespeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Prophetic Flamespeaker deals combat damage to a player, exile the top card of your library. You may play it this turn.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new PropheticFlamespeakerExileEffect(), false));
    }

    private PropheticFlamespeaker(final PropheticFlamespeaker card) {
        super(card);
    }

    @Override
    public PropheticFlamespeaker copy() {
        return new PropheticFlamespeaker(this);
    }
}

class PropheticFlamespeakerExileEffect extends OneShotEffect {

    public PropheticFlamespeakerExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "exile the top card of your library. You may play it this turn";
    }

    private PropheticFlamespeakerExileEffect(final PropheticFlamespeakerExileEffect effect) {
        super(effect);
    }

    @Override
    public PropheticFlamespeakerExileEffect copy() {
        return new PropheticFlamespeakerExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                if (controller.moveCardsToExile(card, source, game, true, source.getSourceId(),
                        CardUtil.createObjectRealtedWindowTitle(source, game, "<this card may be played the turn it was exiled>"))) {
                    ContinuousEffect effect = new PropheticFlamespeakerCastFromExileEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}

class PropheticFlamespeakerCastFromExileEffect extends AsThoughEffectImpl {

    public PropheticFlamespeakerCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may play the card from exile";
    }

    private PropheticFlamespeakerCastFromExileEffect(final PropheticFlamespeakerCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PropheticFlamespeakerCastFromExileEffect copy() {
        return new PropheticFlamespeakerCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && objectId.equals(getTargetPointer().getFirst(game, source));
    }
}

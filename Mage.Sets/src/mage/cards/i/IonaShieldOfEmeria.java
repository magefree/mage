
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public final class IonaShieldOfEmeria extends CardImpl {

    public IonaShieldOfEmeria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{W}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As Iona, Shield of Emeria enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Benefit)));

        // Your opponents can't cast spells of the chosen color.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new IonaShieldOfEmeriaReplacementEffect()));

    }

    private IonaShieldOfEmeria(final IonaShieldOfEmeria card) {
        super(card);
    }

    @Override
    public IonaShieldOfEmeria copy() {
        return new IonaShieldOfEmeria(this);
    }
}

class IonaShieldOfEmeriaReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    IonaShieldOfEmeriaReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Your opponents can't cast spells of the chosen color";
    }

    IonaShieldOfEmeriaReplacementEffect(final IonaShieldOfEmeriaReplacementEffect effect) {
        super(effect);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        ObjectColor chosenColor = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        MageObject mageObject = game.getObject(source);
        if (mageObject != null && chosenColor != null) {
            return "You can't cast " + chosenColor.toString() + " spells (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            ObjectColor chosenColor = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
            // spell is not on the stack yet, so we have to check the card
            Card card = game.getCard(event.getSourceId());
            if (chosenColor != null && card != null && card.getColor(game).contains(chosenColor)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IonaShieldOfEmeriaReplacementEffect copy() {
        return new IonaShieldOfEmeriaReplacementEffect(this);
    }
}

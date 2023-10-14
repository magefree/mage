
package mage.cards.f;

import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;
import mage.players.ManaPoolItem;

/**
 *
 * @author notgreat
 */
public final class FalseDawn extends CardImpl {

    public FalseDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");

        this.getSpellAbility().addEffect(new FalseDawnManaAddEffect());
        this.getSpellAbility().addEffect(new FalseDawnManaSpendEffect());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private FalseDawn(final FalseDawn card) {
        super(card);
    }

    @Override
    public FalseDawn copy() {
        return new FalseDawn(this);
    }
}
class FalseDawnManaAddEffect extends ReplacementEffectImpl {

    FalseDawnManaAddEffect() {
        super(Duration.EndOfTurn, Outcome.Neutral);
        staticText = "Until end of turn, spells and abilities you control that would add colored mana instead add "+
                "that much white mana.";
    }

    private FalseDawnManaAddEffect(final FalseDawnManaAddEffect effect) {
        super(effect);
    }

    @Override
    public FalseDawnManaAddEffect copy() {
        return new FalseDawnManaAddEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Mana mana = ((ManaEvent) event).getMana();
        mana.setWhite(mana.countColored());
        mana.setBlue(0);
        mana.setBlack(0);
        mana.setRed(0);
        mana.setGreen(0);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_MANA;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getControllerId(event.getSourceId()).equals(source.getControllerId());
    }
}
//Based on Celestial Dawn
class FalseDawnManaSpendEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public FalseDawnManaSpendEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Until end of turn, you may spend white mana as though it were mana of any color.";
    }

    private FalseDawnManaSpendEffect(final FalseDawnManaSpendEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public FalseDawnManaSpendEffect copy() {
        return new FalseDawnManaSpendEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId);
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        if (mana.getWhite() > 0) {
            return ManaType.WHITE;
        }
        return null;
    }
}
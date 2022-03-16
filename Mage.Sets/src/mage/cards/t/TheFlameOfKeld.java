
package mage.cards.t;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author JRHerlehy
 */
public final class TheFlameOfKeld extends CardImpl {

    public TheFlameOfKeld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Discard your hand.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new DiscardHandControllerEffect());

        // II — Draw two cards.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new DrawCardSourceControllerEffect(2));

        // III — If a red source you control would deal damage to a permanent or player this turn, it deals that much damage plus 2 to that permanent or player instead.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new TheFlameOfKeldDamageEffect());

        this.addAbility(sagaAbility);
    }

    private TheFlameOfKeld(final TheFlameOfKeld card) {
        super(card);
    }

    @Override
    public TheFlameOfKeld copy() {
        return new TheFlameOfKeld(this);
    }
}

class TheFlameOfKeldDamageEffect extends ReplacementEffectImpl {

    public TheFlameOfKeldDamageEffect() {
        super(Duration.EndOfTurn, Outcome.Damage);
        this.staticText = "If a red source you control would deal damage to a permanent or player this turn, it deals that much damage plus 2 to that permanent or player instead";
    }

    public TheFlameOfKeldDamageEffect(final TheFlameOfKeldDamageEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 2));
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (source.isControlledBy(game.getControllerId(event.getSourceId()))) {
            MageObject sourceObject;
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (sourcePermanent == null) {
                sourceObject = game.getObject(event.getSourceId());
            } else {
                sourceObject = sourcePermanent;
            }
            return sourceObject != null && sourceObject.getColor(game).isRed() && !sourceObject.getId().equals(source.getSourceId());
        }
        return false;
    }

    @Override
    public TheFlameOfKeldDamageEffect copy() {
        return new TheFlameOfKeldDamageEffect(this);
    }

}

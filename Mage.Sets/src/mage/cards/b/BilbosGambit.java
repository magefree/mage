package mage.cards.b;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.GiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.GiftType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetSpell;

/**
 *
 * @author muz
 */
public final class BilbosGambit extends CardImpl {

    public BilbosGambit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Gift a Treasure
        this.addAbility(new GiftAbility(this, GiftType.TREASURE));

        // Return target spell to its owner's hand. If the gift was promised, players can't cast spells this turn.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
            new AddContinuousEffectToGame(new BilbosGambitEffect()),
            GiftWasPromisedCondition.TRUE,
            "if the gift was promised, players can't cast spells this turn"
        ));
    }

    private BilbosGambit(final BilbosGambit card) {
        super(card);
    }

    @Override
    public BilbosGambit copy() {
        return new BilbosGambit(this);
    }
}

class BilbosGambitEffect extends ContinuousRuleModifyingEffectImpl {

    BilbosGambitEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "players can't cast spells this turn";
    }

    private BilbosGambitEffect(final BilbosGambitEffect effect) {
        super(effect);
    }

    @Override
    public BilbosGambitEffect copy() {
        return new BilbosGambitEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast spells this turn (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
}

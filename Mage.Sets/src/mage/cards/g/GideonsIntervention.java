package mage.cards.g;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.PreventDamageEvent;
import mage.game.events.PreventedDamageEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class GideonsIntervention extends CardImpl {

    public GideonsIntervention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // As Gideon's Intervention enters the battlefield, choose a card name.
        Effect effect = new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL);
        effect.setText("choose a card name");
        this.addAbility(new AsEntersBattlefieldAbility(effect));

        // Your opponents can't cast spells with the chosen name.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GideonsInterventionCantCastEffect()));

        // Prevent all damage that would be dealt to you and permanents you control by sources with the chosen name.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GideonsInterventionPreventAllDamageEffect()));
    }

    private GideonsIntervention(final GideonsIntervention card) {
        super(card);
    }

    @Override
    public GideonsIntervention copy() {
        return new GideonsIntervention(this);
    }
}

class GideonsInterventionCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    public GideonsInterventionCantCastEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Your opponents can't cast spells with the chosen name";
    }

    public GideonsInterventionCantCastEffect(final GideonsInterventionCantCastEffect effect) {
        super(effect);
    }

    @Override
    public GideonsInterventionCantCastEffect copy() {
        return new GideonsInterventionCantCastEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (mageObject != null && cardName != null) {
            return "You can't cast a card named " + cardName + " (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return (event.getType() == GameEvent.EventType.CAST_SPELL_LATE || event.getType() == GameEvent.EventType.CAST_SPELL);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            MageObject object = game.getObject(event.getSourceId());
            return object != null && object.getName().equals(cardName);
        }
        return false;
    }
}

class GideonsInterventionPreventAllDamageEffect extends PreventionEffectImpl {

    public GideonsInterventionPreventAllDamageEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Prevent all damage that would be dealt to you and permanents you control by sources with the chosen name.";
    }

    public GideonsInterventionPreventAllDamageEffect(final GideonsInterventionPreventAllDamageEffect effect) {
        super(effect);
    }

    @Override
    public GideonsInterventionPreventAllDamageEffect copy() {
        return new GideonsInterventionPreventAllDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new PreventDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), event.getAmount(), ((DamageEvent) event).isCombatDamage());
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            event.setAmount(0);
            game.informPlayers("Damage has been prevented: " + damage);
            game.fireEvent(new PreventedDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), damage));
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        Permanent targetPerm = game.getPermanent(event.getTargetId());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (object != null && (event.getType() == EventType.DAMAGE_PLAYER
                || targetPerm != null && event.getType() == EventType.DAMAGE_PERMANENT)) {
            if (CardUtil.haveSameNames(object, cardName, game)
                    && (event.getTargetId().equals(source.getControllerId())
                    || targetPerm != null && targetPerm.isControlledBy(source.getControllerId()))) {
                return super.applies(event, source, game);
            }
        }
        return false;
    }
}

package mage.cards.y;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author emerald000
 */
public final class YixlidJailer extends CardImpl {

    public YixlidJailer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Cards in graveyards lose all abilities.
        this.addAbility(new SimpleStaticAbility(new YixlidJailerEffect()));
        this.addAbility(new SimpleStaticAbility(new YixlidJailerRulesEffect()));
    }

    private YixlidJailer(final YixlidJailer card) {
        super(card);
    }

    @Override
    public YixlidJailer copy() {
        return new YixlidJailer(this);
    }
}

class YixlidJailerEffect extends ContinuousEffectImpl {

    YixlidJailerEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.LoseAbility);
        staticText = "Cards in graveyards lose all abilities.";

        this.effectCardZones.add(Zone.GRAVEYARD);
        this.playersToCheck = TargetController.EACH_PLAYER;
    }

    private YixlidJailerEffect(final YixlidJailerEffect effect) {
        super(effect);
    }

    @Override
    public YixlidJailerEffect copy() {
        return new YixlidJailerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (MageObjectReference mor : this.affectedObjectList) {
            Card card = mor.getCard(game);
            if (card == null) {
                continue;
            }
            card.looseAllAbilities(game);
        }
        return true;
    }

    @Override
    public int calculateResult(Game game, Ability source, List<MageObject> affectedObjects) {
        return affectedObjects.size();
    }
}

class YixlidJailerRulesEffect extends ContinuousRuleModifyingEffectImpl {

    YixlidJailerRulesEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, false, false);
    }

    private YixlidJailerRulesEffect(final YixlidJailerRulesEffect effect) {
        super(effect);
    }

    @Override
    public YixlidJailerRulesEffect copy() {
        return new YixlidJailerRulesEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Object targetAbility = getValue("targetAbility");
        if (targetAbility instanceof ZoneChangeTriggeredAbility) {
            ZoneChangeTriggeredAbility zoneAbility = (ZoneChangeTriggeredAbility) targetAbility;
            return zoneAbility.getFromZone() != Zone.BATTLEFIELD && zoneAbility.getToZone() == Zone.GRAVEYARD;
        }
        return false;
    }
}

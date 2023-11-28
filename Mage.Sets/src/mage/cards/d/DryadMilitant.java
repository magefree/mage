
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author LevelX2
 */
public final class DryadMilitant extends CardImpl {

    public DryadMilitant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G/W}");
        this.subtype.add(SubType.DRYAD);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // If an instant or sorcery card would be put into a graveyard from anywhere, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DryadMilitantReplacementEffect()));
    }

    private DryadMilitant(final DryadMilitant card) {
        super(card);
    }

    @Override
    public DryadMilitant copy() {
        return new DryadMilitant(this);
    }
}

class DryadMilitantReplacementEffect extends ReplacementEffectImpl {

    public DryadMilitantReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If an instant or sorcery card would be put into a graveyard from anywhere, exile it instead";
    }

    private DryadMilitantReplacementEffect(final DryadMilitantReplacementEffect effect) {
        super(effect);
    }

    @Override
    public DryadMilitantReplacementEffect copy() {
        return new DryadMilitantReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (((ZoneChangeEvent)event).getToZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && (card.isSorcery(game) || card.isInstant(game))) {
                return true;
            }
        }
        return false;
    }
}

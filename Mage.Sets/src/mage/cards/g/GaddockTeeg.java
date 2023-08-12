
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Plopman
 */
public final class GaddockTeeg extends CardImpl {

    public GaddockTeeg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Noncreature spells with converted mana cost 4 or greater can't be cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GaddockTeegReplacementEffect4()));
        // Noncreature spells with {X} in their mana costs can't be cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GaddockTeegReplacementEffectX()));
    }

    private GaddockTeeg(final GaddockTeeg card) {
        super(card);
    }

    @Override
    public GaddockTeeg copy() {
        return new GaddockTeeg(this);
    }
}

class GaddockTeegReplacementEffect4 extends ContinuousRuleModifyingEffectImpl {

    public GaddockTeegReplacementEffect4() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Noncreature spells with mana value 4 or greater can't be cast";
    }

    public GaddockTeegReplacementEffect4(final GaddockTeegReplacementEffect4 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GaddockTeegReplacementEffect4 copy() {
        return new GaddockTeegReplacementEffect4(this);
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getSourceId());
        if (card != null && !card.isCreature(game) && card.getManaValue() >= 4) {
            return true;
        }
        return false;
    }

}

class GaddockTeegReplacementEffectX extends ContinuousRuleModifyingEffectImpl {

    public GaddockTeegReplacementEffectX() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Noncreature spells with {X} in their mana costs can't be cast";
    }

    public GaddockTeegReplacementEffectX(final GaddockTeegReplacementEffectX effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GaddockTeegReplacementEffectX copy() {
        return new GaddockTeegReplacementEffectX(this);
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getSourceId());
        if (card != null && !card.isCreature(game) && card.getManaCost().getText().contains("X")) {
            return true;
        }
        return false;
    }

}


package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;

/**
 *
 * @author Loki
 */
public final class SolkanarTheSwampKing extends CardImpl {

    public SolkanarTheSwampKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(new SwampwalkAbility());
        // Whenever a player casts a black spell, you gain 1 life.
        this.addAbility(new SolkanarTheSwampKingAbility());

    }

    private SolkanarTheSwampKing(final SolkanarTheSwampKing card) {
        super(card);
    }

    @Override
    public SolkanarTheSwampKing copy() {
        return new SolkanarTheSwampKing(this);
    }
}

class SolkanarTheSwampKingAbility extends TriggeredAbilityImpl {

    public SolkanarTheSwampKingAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(1), false);
    }

    public SolkanarTheSwampKingAbility(final SolkanarTheSwampKingAbility ability) {
        super(ability);
    }

    @Override
    public SolkanarTheSwampKingAbility copy() {
        return new SolkanarTheSwampKingAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && spell.getColor(game).isBlack();
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a black spell, you gain 1 life.";
    }
}

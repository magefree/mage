

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;

/**
 *
 * @author Loki
 */
public final class KorFirewalker extends CardImpl {

    public KorFirewalker (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SOLDIER);

        this.color.setWhite(true);        
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
        this.addAbility(new KorFirewalkerAbility());

    }

    private KorFirewalker(final KorFirewalker card) {
        super(card);
    }

    @Override
    public KorFirewalker copy() {
        return new KorFirewalker(this);
    }

}

class KorFirewalkerAbility extends TriggeredAbilityImpl {

    public KorFirewalkerAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(1), true);
    }

    private KorFirewalkerAbility(final KorFirewalkerAbility ability) {
        super(ability);
    }

    @Override
    public KorFirewalkerAbility copy() {
        return new KorFirewalkerAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && spell.getColor(game).isRed();
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a red spell, you may gain 1 life.";
    }
}
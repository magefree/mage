
package mage.cards.p;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.mana.AddManaChosenColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author jeffwadsworth
 */
public final class ParadisePlume extends CardImpl {

    public ParadisePlume(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // As Paradise Plume enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Detriment)));

        // Whenever a player casts a spell of the chosen color, you may gain 1 life.
        this.addAbility(new ParadisePlumeSpellCastTriggeredAbility());

        // {tap}: Add one mana of the chosen color.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaChosenColorEffect(), new TapSourceCost()));

    }

    private ParadisePlume(final ParadisePlume card) {
        super(card);
    }

    @Override
    public ParadisePlume copy() {
        return new ParadisePlume(this);
    }
}

class ParadisePlumeSpellCastTriggeredAbility extends TriggeredAbilityImpl {

    public ParadisePlumeSpellCastTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(1), true);
    }

    public ParadisePlumeSpellCastTriggeredAbility(final ParadisePlumeSpellCastTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ObjectColor color = (ObjectColor) game.getState().getValue(getSourceId() + "_color");
        if (color != null) {
            FilterSpell filter = new FilterSpell();
            filter.add(new ColorPredicate(color));
            Spell spell = game.getStack().getSpell(event.getTargetId());
            return (spell != null
                    && filter.match(spell, getControllerId(), this, game));
        }
        return false;
    }

    @Override
    public ParadisePlumeSpellCastTriggeredAbility copy() {
        return new ParadisePlumeSpellCastTriggeredAbility(this);
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a player casts a spell of the chosen color, " ;
    }
}

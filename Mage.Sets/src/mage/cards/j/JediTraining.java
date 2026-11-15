
package mage.cards.j;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.AbilitiesCostReductionControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.MeditateAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterAbility;
import mage.filter.FilterCard;
import mage.filter.predicate.other.AbilityClassPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author Styxo
 */
public final class JediTraining extends CardImpl {

    protected static final FilterCard filter = new FilterCard("Jedi spells");
    private static final FilterAbility filterMeditate = new FilterAbility("meditate abilities you activate");

    static {
        filter.add(SubType.JEDI.getPredicate());
        filterMeditate.add(new AbilityClassPredicate(MeditateAbility.class));
    }

    public JediTraining(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}");

        // Meditate abilities you activate cost {1} less to activate.
        this.addAbility(new SimpleStaticAbility(new AbilitiesCostReductionControllerEffect(filterMeditate)));

        // Whenever a Jedi creature you control meditates, scry 1.
        this.addAbility(new JediTrainingTriggeredAbility());
    }

    private JediTraining(final JediTraining card) {
        super(card);
    }

    @Override
    public JediTraining copy() {
        return new JediTraining(this);
    }
}

class JediTrainingTriggeredAbility extends TriggeredAbilityImpl {

    public JediTrainingTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ScryEffect(1, false));
        setTriggerPhrase("Whenever a Jedi creature you control meditates, ");
    }

    private JediTrainingTriggeredAbility(final JediTrainingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JediTrainingTriggeredAbility copy() {
        return new JediTrainingTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MEDITATED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {        
        Card source = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return event.getPlayerId().equals(getControllerId()) && source != null && JediTraining.filter.match(source, game);
    }
}


package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.ChancellorAbility;
import mage.abilities.effects.common.SetPlayerLifeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author LevelX2
 */
public final class Providence extends CardImpl {

    private static String abilityText = "at the beginning of the first upkeep, your life total becomes 26";

    public Providence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{W}{W}");

        // You may reveal this card from your opening hand. If you do, at the beginning of the first upkeep, your life total becomes 26.
        Ability ability = new ChancellorAbility(new ProvidenceDelayedTriggeredAbility(), abilityText);
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        // Your life total becomes 26.
        this.getSpellAbility().addEffect(new SetPlayerLifeSourceEffect(26));

    }

    private Providence(final Providence card) {
        super(card);
    }

    @Override
    public Providence copy() {
        return new Providence(this);
    }
}

class ProvidenceDelayedTriggeredAbility extends DelayedTriggeredAbility {

    ProvidenceDelayedTriggeredAbility() {
        super(new SetPlayerLifeSourceEffect(26));
    }

    private ProvidenceDelayedTriggeredAbility(final ProvidenceDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public ProvidenceDelayedTriggeredAbility copy() {
        return new ProvidenceDelayedTriggeredAbility(this);
    }
}


package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class DuelistsHeritage extends CardImpl {

    public DuelistsHeritage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");


        // Whenever one or more creatures attack, you may have target attacking creature gain double strike until end of turn.
        Ability ability = new DuelistsHeritageTriggeredAbility();
        ability.addTarget(new TargetCreaturePermanent(new FilterAttackingCreature()));
        this.addAbility(ability);
    }

    public DuelistsHeritage(final DuelistsHeritage card) {
        super(card);
    }

    @Override
    public DuelistsHeritage copy() {
        return new DuelistsHeritage(this);
    }
}

class DuelistsHeritageTriggeredAbility extends TriggeredAbilityImpl {

    public DuelistsHeritageTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn), true);
    }

    public DuelistsHeritageTriggeredAbility(final DuelistsHeritageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DuelistsHeritageTriggeredAbility copy() {
        return new DuelistsHeritageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !game.getCombat().getAttackers().isEmpty();
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures attack, " + super.getRule();
    }
}

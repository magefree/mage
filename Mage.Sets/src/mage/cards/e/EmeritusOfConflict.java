package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.SpellsCastWatcher;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class EmeritusOfConflict extends PrepareCard {

    public EmeritusOfConflict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}", "Lightning Bolt", CardType.INSTANT, "{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever you cast your third spell each turn, this creature becomes prepared.
        this.addAbility(new EmeritusOfConflictTriggeredAbility());

        // Lightning Bolt
        // Instant {R}
        // Lightning Bolt deals 3 damage to any target.
        this.getSpellCard().getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellCard().getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private EmeritusOfConflict(final EmeritusOfConflict card) {
        super(card);
    }

    @Override
    public EmeritusOfConflict copy() {
        return new EmeritusOfConflict(this);
    }
}

class EmeritusOfConflictTriggeredAbility extends TriggeredAbilityImpl {

    public EmeritusOfConflictTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BecomePreparedSourceEffect());
        setTriggerPhrase("Whenever you cast your third spell each turn, ");
    }

    private EmeritusOfConflictTriggeredAbility(final EmeritusOfConflictTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EmeritusOfConflictTriggeredAbility copy() {
        return new EmeritusOfConflictTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (this.isControlledBy(event.getPlayerId())) {
            SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
            return watcher != null && watcher.getSpellsCastThisTurn(this.getControllerId()).size() == 3;
        }
        return false;
    }
}

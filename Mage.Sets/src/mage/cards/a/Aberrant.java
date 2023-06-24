package mage.cards.a;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.RavenousAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Aberrant extends CardImpl {

    public Aberrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{1}{G}");

        this.subtype.add(SubType.TYRANID);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Ravenous
        this.addAbility(new RavenousAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Heavy Power Hammer -- Whenever Aberrant deals combat damage to a player, destroy target artifact or enchantment that player controls.
        this.addAbility(new AberrantTriggeredAbility());
    }

    private Aberrant(final Aberrant card) {
        super(card);
    }

    @Override
    public Aberrant copy() {
        return new Aberrant(this);
    }
}

class AberrantTriggeredAbility extends TriggeredAbilityImpl {

    AberrantTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect()
                .setText("destroy target artifact or enchantment that player controls"), false
        );
        this.withFlavorWord("Heavy Power Hammer");
        this.setTriggerPhrase("Whenever {this} deals combat damage to a player, ");
    }

    private AberrantTriggeredAbility(final AberrantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AberrantTriggeredAbility copy() {
        return new AberrantTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player == null
                || !event.getSourceId().equals(this.getSourceId())
                || !((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        FilterPermanent filter = new FilterArtifactOrEnchantmentPermanent(
                "artifact or enchantment " + player.getLogName() + " controls"
        );
        filter.add(new ControllerIdPredicate(player.getId()));
        this.getTargets().clear();
        this.addTarget(new TargetPermanent(filter));
        return true;
    }
}

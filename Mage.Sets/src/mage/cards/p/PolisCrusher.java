package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.MonstrousCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class PolisCrusher extends CardImpl {

    private static final FilterCard filter = new FilterCard("enchantments");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public PolisCrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");
        this.subtype.add(SubType.CYCLOPS);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // protection from enchantments
        this.addAbility(new ProtectionAbility(filter));

        // {4}{R}{G}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{4}{R}{G}", 3));

        // Whenever Polis Crusher deals combat damage to a player, if Polis Crusher is monstrous, destroy target enchantment that player controls.
        this.addAbility(new PolisCrusherTriggeredAbility());
    }

    private PolisCrusher(final PolisCrusher card) {
        super(card);
    }

    @Override
    public PolisCrusher copy() {
        return new PolisCrusher(this);
    }
}

class PolisCrusherTriggeredAbility extends TriggeredAbilityImpl {

    public PolisCrusherTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), true);
    }

    private PolisCrusherTriggeredAbility(final PolisCrusherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PolisCrusherTriggeredAbility copy() {
        return new PolisCrusherTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return MonstrousCondition.instance.apply(game, this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.sourceId) && ((DamagedPlayerEvent) event).isCombatDamage()) {
            Player player = game.getPlayer(event.getTargetId());
            if (player != null) {
                FilterPermanent filter = new FilterPermanent("an enchantment controlled by " + player.getLogName());
                filter.add(CardType.ENCHANTMENT.getPredicate());
                filter.add(new ControllerIdPredicate(event.getTargetId()));
                this.getTargets().clear();
                this.addTarget(new TargetPermanent(filter));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player,"
                + " if {this} is monstrous, destroy target enchantment that player controls.";
    }
}

package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class InkEyesServantOfOni extends CardImpl {

    public InkEyesServantOfOni(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.NINJA);
        addSuperType(SuperType.LEGENDARY);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Ninjutsu {3}{B}{B} ({3}{B}{B}, Return an unblocked attacker you control to hand: Put this card onto the battlefield from your hand tapped and attacking.)
        this.addAbility(new NinjutsuAbility("{3}{B}{B}"));

        // Whenever Ink-Eyes, Servant of Oni deals combat damage to a player, you may put target creature card from that player's graveyard onto the battlefield under your control.
        this.addAbility(new InkEyesServantOfOniTriggeredAbility());

        // {1}{B}: Regenerate Ink-Eyes.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{1}{B}")));
    }

    private InkEyesServantOfOni(final InkEyesServantOfOni card) {
        super(card);
    }

    @Override
    public InkEyesServantOfOni copy() {
        return new InkEyesServantOfOni(this);
    }
}

class InkEyesServantOfOniTriggeredAbility extends TriggeredAbilityImpl {

    public InkEyesServantOfOniTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), true);
    }

    public InkEyesServantOfOniTriggeredAbility(final InkEyesServantOfOniTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public InkEyesServantOfOniTriggeredAbility copy() {
        return new InkEyesServantOfOniTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(this.sourceId) || !((DamagedPlayerEvent) event).isCombatDamage()) {
            return false;
        }
        Player damagedPlayer = game.getPlayer(event.getTargetId());
        if (damagedPlayer == null) {
            return false;
        }
        FilterCard filter = new FilterCard("creature in " + damagedPlayer.getName() + "'s graveyard");
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(new OwnerIdPredicate(damagedPlayer.getId()));
        TargetCardInGraveyard target = new TargetCardInGraveyard(filter);
        this.getTargets().clear();
        this.addTarget(target);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, "
                + "you may put target creature card from that player's "
                + "graveyard onto the battlefield under your control.";
    }
}

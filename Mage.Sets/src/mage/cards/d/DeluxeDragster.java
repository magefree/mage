package mage.cards.d;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeluxeDragster extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("except by Vehicles");

    static {
        filter.add(Predicates.not(SubType.VEHICLE.getPredicate()));
    }

    public DeluxeDragster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{U}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Deluxe Dragster can’t be blocked except by Vehicles.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

        // Whenever Deluxe Dragster deals combat damage to a player, you may cast target instant or sorcery card from
        // that player’s graveyard without paying its mana cost. If that spell would be put into a graveyard, exile it instead.
        this.addAbility(new DeluxeDragsterTriggeredAbility());

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private DeluxeDragster(final DeluxeDragster card) {
        super(card);
    }

    @Override
    public DeluxeDragster copy() {
        return new DeluxeDragster(this);
    }
}

class DeluxeDragsterTriggeredAbility extends TriggeredAbilityImpl {

    public DeluxeDragsterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DeluxeDragsterEffect(), true);
    }

    public DeluxeDragsterTriggeredAbility(final DeluxeDragsterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DeluxeDragsterTriggeredAbility copy() {
        return new DeluxeDragsterTriggeredAbility(this);
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
        FilterCard filter = new FilterCard("instant or sorcery in " + damagedPlayer.getName() + "'s graveyard");
        filter.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
        filter.add(new OwnerIdPredicate(damagedPlayer.getId()));
        TargetCardInGraveyard target = new TargetCardInGraveyard(filter);
        this.getTargets().clear();
        this.addTarget(target);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, "
                + "you may cast target instant or sorcery card from "
                + "that player's graveyard without paying its mana cost. "
                + ThatSpellGraveyardExileReplacementEffect.RULE_A;
    }
}

class DeluxeDragsterEffect extends OneShotEffect {

    public DeluxeDragsterEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "cast target instant or sorcery card from "
                + "that player's graveyard without paying its mana cost. "
                + ThatSpellGraveyardExileReplacementEffect.RULE_A;
    }

    public DeluxeDragsterEffect(final DeluxeDragsterEffect effect) {
        super(effect);
    }

    @Override
    public DeluxeDragsterEffect copy() {
        return new DeluxeDragsterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (controller == null || card == null) {
            return false;
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
        controller.cast(controller.chooseAbilityForCast(card, game, true),
                game, true, new ApprovingObject(source, game));
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
        ContinuousEffect effect = new ThatSpellGraveyardExileReplacementEffect(false);
        effect.setTargetPointer(new FixedTarget(card, game));
        game.addEffect(effect, source);
        return true;
    }
}

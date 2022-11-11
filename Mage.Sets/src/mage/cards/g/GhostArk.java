package mage.cards.g;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class GhostArk extends CardImpl {

    public GhostArk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Repair Barge -- Whenever Ghost Ark becomes crewed, each artifact creature card in your graveyard gains unearth {3} until end of turn.
        this.addAbility(new GhostArkTriggeredAbility());

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private GhostArk(final GhostArk card) {
        super(card);
    }

    @Override
    public GhostArk copy() {
        return new GhostArk(this);
    }
}

class GhostArkTriggeredAbility extends TriggeredAbilityImpl {

    GhostArkTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GhostArkEffect());
        setTriggerPhrase("Whenever {this} becomes crewed, ");
        this.withFlavorWord("Repair Barge");
    }

    private GhostArkTriggeredAbility(final GhostArkTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GhostArkTriggeredAbility copy() {
        return new GhostArkTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.VEHICLE_CREWED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(getSourceId());
    }
}

class GhostArkEffect extends ContinuousEffectImpl {

    private static final FilterCard filter = new FilterArtifactCard();

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    GhostArkEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "each artifact creature card in your graveyard gains unearth {3} until end of turn";
    }

    private GhostArkEffect(final GhostArkEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return;
        }
        this.affectedObjectList.addAll(
                player.getGraveyard()
                        .getCards(filter, game)
                        .stream().map(card -> new MageObjectReference(card, game))
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (MageObjectReference mor : this.affectedObjectList) {
            Card card = mor.getCard(game);
            if (card == null) {
                continue;
            }
            UnearthAbility ability = new UnearthAbility(new ManaCostsImpl<>("{3}"));
            ability.setSourceId(card.getId());
            ability.setControllerId(card.getOwnerId());
            game.getState().addOtherAbility(card, ability);
        }
        return true;
    }

    @Override
    public GhostArkEffect copy() {
        return new GhostArkEffect(this);
    }
}
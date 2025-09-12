package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GoldenGuardian extends CardImpl {

    public GoldenGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.secondSideCardClazz = mage.cards.g.GoldForgeGarrison.class;

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {2}: Golden Guardian fights another target creature you control. When Golden Guardian dies this turn, return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(new FightTargetSourceEffect(), new GenericManaCost(2));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new GoldenGuardianDelayedTriggeredAbility(), false));
        this.addAbility(ability);
    }

    private GoldenGuardian(final GoldenGuardian card) {
        super(card);
    }

    @Override
    public GoldenGuardian copy() {
        return new GoldenGuardian(this);
    }
}

class GoldenGuardianDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public GoldenGuardianDelayedTriggeredAbility() {
        super(new GoldenGuardianReturnTransformedEffect(), Duration.EndOfTurn);
        setTriggerPhrase("When {this} dies this turn, ");
    }

    private GoldenGuardianDelayedTriggeredAbility(final GoldenGuardianDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GoldenGuardianDelayedTriggeredAbility copy() {
        return new GoldenGuardianDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return ((ZoneChangeEvent) event).isDiesEvent() && event.getTargetId().equals(getSourceId());
    }
}

class GoldenGuardianReturnTransformedEffect extends OneShotEffect {

    GoldenGuardianReturnTransformedEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return it to the battlefield transformed under your control";
    }

    private GoldenGuardianReturnTransformedEffect(final GoldenGuardianReturnTransformedEffect effect) {
        super(effect);
    }

    @Override
    public GoldenGuardianReturnTransformedEffect copy() {
        return new GoldenGuardianReturnTransformedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || game.getState().getZone(source.getSourceId()) != Zone.GRAVEYARD) {
            return false;
        }
        game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        Card card = game.getCard(source.getSourceId());
        return card != null && controller.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}

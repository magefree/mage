package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WeddingRing extends CardImpl {

    public WeddingRing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}{W}");

        // When Wedding Ring enters the battlefield, if it was cast, target opponent creates a token that's a copy of it.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new WeddingRingEffect()),
                CastFromEverywhereSourceCondition.instance, "When {this} enters the battlefield, " +
                "if it was cast, target opponent creates a token that's a copy of it."
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Whenever an opponent who controls an artifact named Wedding Ring draws a card during their turn, you draw a card.
        this.addAbility(new WeddingRingTriggeredAbility(true));

        // Whenever an opponent who controls an artifact named Wedding Ring gains life during their turn, you gain that much life.
        this.addAbility(new WeddingRingTriggeredAbility(false));
    }

    private WeddingRing(final WeddingRing card) {
        super(card);
    }

    @Override
    public WeddingRing copy() {
        return new WeddingRing(this);
    }
}

class WeddingRingEffect extends OneShotEffect {

    WeddingRingEffect() {
        super(Outcome.Benefit);
    }

    private WeddingRingEffect(final WeddingRingEffect effect) {
        super(effect);
    }

    @Override
    public WeddingRingEffect copy() {
        return new WeddingRingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (player == null || permanent == null) {
            return false;
        }
        return new CreateTokenCopyTargetEffect(player.getId())
                .setTargetPointer(new FixedTarget(permanent, game))
                .apply(game, source);
    }
}

class WeddingRingTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent();

    static {
        filter.add(new NamePredicate("Wedding Ring"));
    }

    private final boolean drawCards;

    WeddingRingTriggeredAbility(boolean drawCards) {
        super(Zone.BATTLEFIELD, null);
        this.drawCards = drawCards;
    }

    private WeddingRingTriggeredAbility(final WeddingRingTriggeredAbility ability) {
        super(ability);
        this.drawCards = ability.drawCards;
    }

    @Override
    public WeddingRingTriggeredAbility copy() {
        return new WeddingRingTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return drawCards
                ? event.getType() == GameEvent.EventType.DREW_CARD
                : event.getType() == GameEvent.EventType.GAINED_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.isActivePlayer(event.getPlayerId())
                || !game.getOpponents(getControllerId()).contains(event.getPlayerId())
                || !game.getBattlefield().contains(filter, getSourceId(), event.getPlayerId(), this, game, 1)) {
            return false;
        }
        this.getEffects().clear();
        if (drawCards) {
            this.addEffect(new DrawCardSourceControllerEffect(1));
        } else {
            this.addEffect(new GainLifeEffect(event.getAmount()));
        }
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent who controls an artifact named Wedding Ring " +
                (drawCards ? "draws a card during their turn, you draw a card."
                        : "gains life during their turn, you gain that much life.");
    }
}

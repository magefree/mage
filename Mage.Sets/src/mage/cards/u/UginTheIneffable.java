package mage.cards.u;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.UginTheIneffableToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

import static mage.constants.Outcome.Benefit;

/**
 * @author TheElk801
 */
public final class UginTheIneffable extends CardImpl {

    private static final FilterCard filter = new FilterCard();
    private static final FilterPermanent filter2 = new FilterPermanent("permanent that's one or more colors");

    static {
        filter.add(ColorlessPredicate.instance);
        filter2.add(Predicates.not(ColorlessPredicate.instance));
    }

    public UginTheIneffable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{6}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.UGIN);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));

        // Colorless spells you cast cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(
                filter, 2
        ).setText("Colorless spells you cast cost {2} less to cast.")));

        // +1: Exile the top card of your library face down and look at it. Create a 2/2 colorless Spirit creature token. When that token leaves the battlefield, put the exiled card into your hand.
        this.addAbility(new LoyaltyAbility(new UginTheIneffableEffect(), 1));

        // -3: Destroy target permanent that's one or more colors.
        Ability ability = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private UginTheIneffable(final UginTheIneffable card) {
        super(card);
    }

    @Override
    public UginTheIneffable copy() {
        return new UginTheIneffable(this);
    }
}

class UginTheIneffableEffect extends OneShotEffect {

    UginTheIneffableEffect() {
        super(Benefit);
        staticText = "Exile the top card of your library face down and look at it. " +
                "Create a 2/2 colorless Spirit creature token. When that token leaves the battlefield, " +
                "put the exiled card into your hand.";
    }

    private UginTheIneffableEffect(final UginTheIneffableEffect effect) {
        super(effect);
    }

    @Override
    public UginTheIneffableEffect copy() {
        return new UginTheIneffableEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        player.lookAtCards(sourcePermanent.getIdName(), card, game);
        player.moveCards(card, Zone.EXILED, source, game);
        card.turnFaceDown(game, source.getControllerId());
        Token token = new UginTheIneffableToken();
        token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
        game.addDelayedTriggeredAbility(new UginTheIneffableDelayedTriggeredAbility(
                new MageObjectReference(token.getLastAddedToken(), game), new MageObjectReference(card, game)
        ), source);
        return true;
    }
}

class UginTheIneffableDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final MageObjectReference tokenRef;
    private final MageObjectReference cardRef;

    UginTheIneffableDelayedTriggeredAbility(MageObjectReference token, MageObjectReference card) {
        super(null, Duration.Custom, true);
        this.tokenRef = token;
        this.cardRef = card;
    }

    private UginTheIneffableDelayedTriggeredAbility(final UginTheIneffableDelayedTriggeredAbility ability) {
        super(ability);
        this.tokenRef = ability.tokenRef;
        this.cardRef = ability.cardRef;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = ((ZoneChangeEvent) event);
        if (!(zEvent.getFromZone() == Zone.BATTLEFIELD)
                || !tokenRef.refersTo(zEvent.getTarget(), game)) {
            this.getEffects().clear();
            Effect effect = new ReturnToHandTargetEffect();
            effect.setTargetPointer(new FixedTarget(cardRef));
            this.addEffect(effect);
            return true;
        }
        return false;
    }

    @Override
    public UginTheIneffableDelayedTriggeredAbility copy() {
        return new UginTheIneffableDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When this token leaves the battlefield, put the exiled card into your hand.";
    }
}

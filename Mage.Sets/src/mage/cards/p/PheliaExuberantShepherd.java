package mage.cards.p;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class PheliaExuberantShepherd extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("other target nonland permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public PheliaExuberantShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Whenever Phelia, Exuberant Shepherd attacks, exile up to one other target nonland permanent. At the beginning of the next end step, return that card to the battlefield under its owner's control. If it entered under your control, put a +1/+1 counter on Phelia.
        Ability ability = new AttacksTriggeredAbility(new PheliaExuberantShepherdExileEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private PheliaExuberantShepherd(final PheliaExuberantShepherd card) {
        super(card);
    }

    @Override
    public PheliaExuberantShepherd copy() {
        return new PheliaExuberantShepherd(this);
    }
}

class PheliaExuberantShepherdExileEffect extends ExileTargetEffect {

    PheliaExuberantShepherdExileEffect() {
        super();
        outcome = Outcome.Neutral; // quite contextual outcome.
        staticText = "exile up to one other target nonland permanent. At the beginning of the next end step, "
                + "return that card to the battlefield under its owner's control. If it entered under your control, "
                + "put a +1/+1 counter on {this}.";
    }

    private PheliaExuberantShepherdExileEffect(final PheliaExuberantShepherdExileEffect effect) {
        super(effect);
    }

    @Override
    public PheliaExuberantShepherdExileEffect copy() {
        return new PheliaExuberantShepherdExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        this.exileId = UUID.randomUUID();
        this.exileZone = sourceObject == null ? null : sourceObject.getIdName();
        // attempting to exile to the fresh exileId
        boolean didSomething = super.apply(game, source);
        // delayed trigger is created even if exiling failed.
        didSomething |= new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                        new PheliaExuberantShepherdEffect(this.exileId)), false
        ).apply(game, source);
        return didSomething;
    }
}

class PheliaExuberantShepherdEffect extends OneShotEffect {

    private final UUID zoneId; // the exile zone's id for cards to return.

    PheliaExuberantShepherdEffect(UUID zoneId) {
        super(Outcome.Benefit);
        staticText = "return that card to the battlefield under its owner's control. "
                + "If it entered under your control, put a +1/+1 counter on {this}";
        this.zoneId = zoneId;
    }

    private PheliaExuberantShepherdEffect(final PheliaExuberantShepherdEffect effect) {
        super(effect);
        this.zoneId = effect.zoneId;
    }

    @Override
    public PheliaExuberantShepherdEffect copy() {
        return new PheliaExuberantShepherdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(zoneId);
        if (exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        Set<Card> cards = exileZone.getCards(game);
        player.moveCards(cards, Zone.BATTLEFIELD, source, game, false, false, true, null);
        game.processAction();

        Permanent phelia = source.getSourcePermanentIfItStillExists(game);
        if (phelia == null) {
            return true; // If phelia is no longer on the battlefield, the +1/+1 part of the effect does not apply.
        }
        boolean enteredUnderYourControl = false;
        for (Card card : cards) {
            // Try to find the permanent that card became
            Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
            if (permanent != null && permanent.getControllerId().equals(source.getControllerId())) {
                enteredUnderYourControl = true;
                break;
            }
        }
        if (!enteredUnderYourControl) {
            return true;
        }
        phelia.addCounters(CounterType.P1P1.createInstance(), source, game);
        return true;
    }
}

package mage.cards.p;

import mage.MageInt;
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
        Ability ability = new AttacksTriggeredAbility(new ExileTargetEffect().setToSourceExileZone(true));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new PheliaExuberantShepherdEffect()), false
        ));
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

class PheliaExuberantShepherdEffect extends OneShotEffect {

    PheliaExuberantShepherdEffect() {
        super(Outcome.Benefit);
        staticText = "return that card to the battlefield under its owner's control. "
                + "If it entered under your control, put a +1/+1 counter on {this}";
    }

    private PheliaExuberantShepherdEffect(final PheliaExuberantShepherdEffect effect) {
        super(effect);
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
        UUID zoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        ExileZone exileZone = game.getExile().getExileZone(zoneId);
        if (exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        Set<Card> cards = exileZone.getCards(game);
        player.moveCards(cards, Zone.BATTLEFIELD, source, game, false, false, true, null);
        game.getState().applyEffects(game);

        Permanent phelia = source.getSourcePermanentIfItStillExists(game);
        if (phelia == null) {
            return true; // If phelia is no longer on the battlefield, the +1/+1 part of the effect does not apply.
        }
        boolean enteredUnderYourControl = false;
        for (Card card : cards) {
            // Try to find the permanent that card became
            Permanent permanent = game.getPermanent(card.getId());
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
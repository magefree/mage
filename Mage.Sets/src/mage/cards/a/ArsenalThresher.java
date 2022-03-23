
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class ArsenalThresher extends CardImpl {

    public ArsenalThresher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W/B}{U}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As Arsenal Thresher enters the battlefield, you may reveal any number of other artifact cards from your hand. Arsenal Thresher enters the battlefield with a +1/+1 counter on it for each card revealed this way.
        this.addAbility(new AsEntersBattlefieldAbility(new ArsenalThresherEffect(),
                "you may reveal any number of other artifact cards from your hand. {this} enters the battlefield with a +1/+1 counter on it for each card revealed this way"));
    }

    private ArsenalThresher(final ArsenalThresher card) {
        super(card);
    }

    @Override
    public ArsenalThresher copy() {
        return new ArsenalThresher(this);
    }
}

class ArsenalThresherEffect extends OneShotEffect {

    public ArsenalThresherEffect() {
        super(Outcome.Benefit);
    }

    public ArsenalThresherEffect(final ArsenalThresherEffect effect) {
        super(effect);
    }

    @Override
    public ArsenalThresherEffect copy() {
        return new ArsenalThresherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent arsenalThresher = game.getPermanentEntering(source.getSourceId());
        FilterArtifactCard filter = new FilterArtifactCard();
        filter.add(AnotherPredicate.instance);
        if (controller.chooseUse(Outcome.Benefit, "Reveal other artifacts in your hand?", source, game)) {
            Cards cards = new CardsImpl();
            if (controller.getHand().count(filter, source.getControllerId(), source, game) > 0) {
                TargetCardInHand target = new TargetCardInHand(0, Integer.MAX_VALUE, filter);
                if (controller.choose(Outcome.Benefit, target, source, game)) {
                    for (UUID uuid : target.getTargets()) {
                        cards.add(controller.getHand().get(uuid, game));
                    }
                    if (arsenalThresher != null) {
                        controller.revealCards(arsenalThresher.getIdName(), cards, game);
                        List<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appliedEffects"); // the basic event is the EntersBattlefieldEvent, so use already applied replacement effects from that event
                        arsenalThresher.addCounters(CounterType.P1P1.createInstance(cards.size()), source.getControllerId(), source, game, appliedEffects);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

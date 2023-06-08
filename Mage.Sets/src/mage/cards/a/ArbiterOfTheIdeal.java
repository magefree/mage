
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class ArbiterOfTheIdeal extends CardImpl {

    public ArbiterOfTheIdeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // <i>Inspired</i> &mdash; Whenever Arbiter of the Ideal becomes untapped, reveal the top card of your library. If it's an artifact, creature, or land card, you may put it onto the battlefield with a manifestation counter on it. It's an enchantment in addition to its other types.
        this.addAbility(new InspiredAbility(new ArbiterOfTheIdealEffect()));
    }

    private ArbiterOfTheIdeal(final ArbiterOfTheIdeal card) {
        super(card);
    }

    @Override
    public ArbiterOfTheIdeal copy() {
        return new ArbiterOfTheIdeal(this);
    }
}

class ArbiterOfTheIdealEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()));
    }

    public ArbiterOfTheIdealEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "reveal the top card of your library. If it's an artifact, creature, or land card, you may put it onto the battlefield with a manifestation counter on it. It's an enchantment in addition to its other types";
    }

    public ArbiterOfTheIdealEffect(final ArbiterOfTheIdealEffect effect) {
        super(effect);
    }

    @Override
    public ArbiterOfTheIdealEffect copy() {
        return new ArbiterOfTheIdealEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card != null) {
            controller.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
            if (filter.match(card, game) && controller.chooseUse(outcome, "Put " + card.getName() + " onto the battlefield?", source, game)) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                Permanent permanent = game.getPermanent(card.getId());
                if (permanent != null) {
                    permanent.addCounters(CounterType.MANIFESTATION.createInstance(), source.getControllerId(), source, game);
                    ContinuousEffect effect = new AddCardTypeTargetEffect(Duration.Custom, CardType.ENCHANTMENT);
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                }
            }
        }
        return true;
    }
}

package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author weirddan455
 */
public final class JhoiraAgelessInnovator extends CardImpl {

    public JhoiraAgelessInnovator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Put two ingenuity counters on Jhoira, Ageless Innovator, then you may put an artifact card with mana value X or less from your hand onto the battlefield, where X is the number of ingenuity counters on Jhoira.
        Ability ability = new SimpleActivatedAbility(new AddCountersSourceEffect(CounterType.INGENUITY.createInstance(2)), new TapSourceCost());
        ability.addEffect(new JhoiraAgelessInnovatorEffect());
        this.addAbility(ability);
    }

    private JhoiraAgelessInnovator(final JhoiraAgelessInnovator card) {
        super(card);
    }

    @Override
    public JhoiraAgelessInnovator copy() {
        return new JhoiraAgelessInnovator(this);
    }
}

class JhoiraAgelessInnovatorEffect extends OneShotEffect {

    public JhoiraAgelessInnovatorEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = ", then you may put an artifact card with mana value X or less from your hand onto the battlefield, where X is the number of ingenuity counters on Jhoira.";
    }

    private JhoiraAgelessInnovatorEffect(final JhoiraAgelessInnovatorEffect effect) {
        super(effect);
    }

    @Override
    public JhoiraAgelessInnovatorEffect copy() {
        return new JhoiraAgelessInnovatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        if (sourcePermanent == null) {
            return false;
        }
        int numCounters = sourcePermanent.getCounters(game).getCount(CounterType.INGENUITY);
        FilterArtifactCard filter = new FilterArtifactCard("artifact card with mana value " + numCounters + " or less from your hand");
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, numCounters + 1));
        TargetCardInHand target = new TargetCardInHand(filter);
        if (!target.canChoose(controller.getId(), source, game) || !controller.chooseUse(outcome, "Put an artifact with mana value " + numCounters + " or less into play?", source, game)) {
            return false;
        }
        controller.chooseTarget(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && controller.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}

package mage.cards.h;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.*;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author grimreap124
 */
public final class HourglassOfTheLost extends CardImpl {


    public HourglassOfTheLost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        // {T}: Add {W}. Put a time counter on Hourglass of the Lost.
        Ability ability = new WhiteManaAbility();
        ability.addEffect(new AddCountersSourceEffect(CounterType.TIME.createInstance()));
        this.addAbility(ability);
        // {T}, Remove X time counters from Hourglass of the Lost and exile it: Return each nonland permanent card with mana value X from your graveyard to the battlefield. Activate only as a sorcery.
        Ability returnAbility = new ActivateAsSorceryActivatedAbility(new HourglassOfTheLostEffect(), null);
        returnAbility.addCost(new TapSourceCost());
        returnAbility.addCost(new RemoveVariableCountersSourceCost(CounterType.TIME));
        returnAbility.addCost(new ExileSourceCost().setText("and exile it"));
        this.addAbility(returnAbility);
    }

    private HourglassOfTheLost(final HourglassOfTheLost card) {
        super(card);
    }

    @Override
    public HourglassOfTheLost copy() {
        return new HourglassOfTheLost(this);
    }
}

class HourglassOfTheLostEffect extends OneShotEffect {

    HourglassOfTheLostEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return each nonland permanent card with mana value X from your graveyard to the battlefield.";
    }

    private HourglassOfTheLostEffect(final HourglassOfTheLostEffect effect) {
        super(effect);
    }

    @Override
    public HourglassOfTheLostEffect copy() {
        return new HourglassOfTheLostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            FilterCard filter = new FilterNonlandCard();
            int xValue = CardUtil.getSourceCostsTag(game, source, "X", 0);
            game.informPlayers(xValue + " Time counters removed");
            filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
            return controller.moveCards(controller.getGraveyard().getCards(filter, game), Zone.BATTLEFIELD, source, game);
        }
        game.informPlayers("controller is null");
        return false;
    }
}

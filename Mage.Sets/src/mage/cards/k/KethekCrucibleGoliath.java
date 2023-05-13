package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author miesma
 */
public final class KethekCrucibleGoliath extends CardImpl {

    public KethekCrucibleGoliath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your end step, you may sacrifice another creature.
        // If you do, reveal cards from the top of your library
        // until you reveal a nonlegendary creature card with lesser mana value, put it onto the battlefield,
        // then put the rest on the bottom of your library in a random order.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new KethekCrucibleGoliathEffect(), TargetController.YOU, false
        ));
    }

    private KethekCrucibleGoliath(final KethekCrucibleGoliath card) {
        super(card);
    }

    @Override
    public KethekCrucibleGoliath copy() {
        return new KethekCrucibleGoliath(this);
    }
}

class KethekCrucibleGoliathEffect extends OneShotEffect {

    KethekCrucibleGoliathEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice another creature. " +
                "If you do, reveal cards from the top of your library " +
                "until you reveal a nonlegendary creature card with lesser mana value" +
                ", put it onto the battlefield, then put the rest on the bottom of your library in a random order.";
    }

    private KethekCrucibleGoliathEffect(final mage.cards.k.KethekCrucibleGoliathEffect effect) {
        super(effect);
    }

    @Override
    public mage.cards.k.KethekCrucibleGoliathEffect copy() {
        return new mage.cards.k.KethekCrucibleGoliathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        // May sacrifice another creature
        TargetPermanent target = (TargetPermanent) new TargetControlledPermanent(
                0, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, true
        ).withChooseHint("to sacrifice");
        player.choose(Outcome.Sacrifice, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null || !permanent.sacrifice(source, game)) {
            return false;
        }
        // If you do, reveal cards from the top of your library (IF = No second trigger on the Stack)
        // until you reveal a nonlegendary creature card with lesser mana value
        int xValue = permanent.getManaValue();
        FilterCreatureCard filterCreatureCard = new FilterCreatureCard("nonlegendary creature card with lesser mana value");
        // Nonlegendary
        filterCreatureCard.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
        // Lesser Mana Value
        filterCreatureCard.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue));
        //put it onto the battlefield, then put the rest on the bottom of your library in a random order.
        RevealCardsFromLibraryUntilEffect effect = new RevealCardsFromLibraryUntilEffect(filterCreatureCard, PutCards.BATTLEFIELD,PutCards.BOTTOM_RANDOM);
        effect.apply(game, source);
        return true;
    }
}

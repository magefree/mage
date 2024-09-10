package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CastFromGraveyardOnceEachTurnAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author justinjohnson14
 */
public final class ArcadeGannon extends CardImpl {

    private static final FilterCard filter = new FilterCard("an artifact or Human spell from your graveyard with mana value less than or equal to the number of quest counters on {this}");

    static{
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                SubType.HUMAN.getPredicate()
        ));
        filter.add(ArcadeGannonPredicate.instance);
    }

    public ArcadeGannon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Draw a card, then discard a card. Put a quest counter on Arcade Gannon.
        Ability ability = (new SimpleActivatedAbility(new DrawDiscardControllerEffect(1,1), new TapSourceCost()));
        ability.addEffect(new AddCountersSourceEffect(CounterType.QUEST.createInstance(1)));
        this.addAbility(ability);

        // For Auld Lang Syne -- Once during each of your turns, you may cast an artifact or Human spell from your graveyard with mana value less than or equal to the number of quest counters on Arcade Gannon.
        this.addAbility(new CastFromGraveyardOnceEachTurnAbility(filter).withFlavorWord("For Auld Lang Syne"));
    }

    private ArcadeGannon(final ArcadeGannon card) {
        super(card);
    }

    @Override
    public ArcadeGannon copy() {
        return new ArcadeGannon(this);
    }
}

enum ArcadeGannonPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        Permanent sourcePermanent = input.getSource().getSourcePermanentOrLKI(game);
        return sourcePermanent != null && input.getObject().getManaValue() <= sourcePermanent.getCounters(game).getCount(CounterType.QUEST);
    }

    @Override
    public String toString() {
        return "mana value less than or equal to {this}'s power";
    }
}

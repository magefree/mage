package mage.cards.w;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jimga150
 */
public final class WishingWell extends CardImpl {

    public WishingWell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{U}");
        

        // {T}: Put a coin counter on Wishing Well.
        // When you do, you may cast target instant or sorcery card with mana value equal to the number of coin counters
        // on Wishing Well from your graveyard without paying its mana cost. If that spell would be put into your
        // graveyard, exile it instead. Activate only as a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new WishingWellEffect(), new TapSourceCost()));
    }

    private WishingWell(final WishingWell card) {
        super(card);
    }

    @Override
    public WishingWell copy() {
        return new WishingWell(this);
    }
}

// Based on AjaniNacatlAvengerZeroEffect and AetherVialEffect
class WishingWellEffect extends OneShotEffect {

    WishingWellEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put a coin counter on {this}. " +
                "When you do, you may cast target instant or sorcery card with mana value equal to the number of coin " +
                "counters on {this} from your graveyard without paying its mana cost. If that spell would be put " +
                "into your graveyard, exile it instead";
    }

    private WishingWellEffect(final WishingWellEffect effect) {
        super(effect);
    }

    @Override
    public WishingWellEffect copy() {
        return new WishingWellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        if (!new AddCountersSourceEffect(CounterType.COIN.createInstance(), true).apply(game, source)) {
            return false;
        }

        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null) {
            return false;
        }
        int count = permanent.getCounters(game).getCount(CounterType.COIN);

        FilterInstantOrSorceryCard filter = new FilterInstantOrSorceryCard("instant or sorcery card with mana value " + count);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, count));

        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
                new MayCastTargetCardEffect(CastManaAdjustment.WITHOUT_PAYING_MANA_COST, true),
                true,
                "When you do, you may cast target instant or sorcery card with mana value equal to the number of " +
                        "coin counters on {this} from your graveyard without paying its mana cost. If that spell " +
                        "would be put into your graveyard, exile it instead"
        );
        reflexive.addTarget(new TargetCardInYourGraveyard(filter));
        game.fireReflexiveTriggeredAbility(reflexive, source);
        return true;
    }
}

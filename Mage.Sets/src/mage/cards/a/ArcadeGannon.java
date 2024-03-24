package mage.cards.a;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.*;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.ManaValueLessThanOrEqualToSourcePowerPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;
import mage.watchers.common.CardsAmountDrawnThisTurnWatcher;
import mage.watchers.common.CastFromGraveyardWatcher;
import mage.watchers.common.OnceEachTurnCastWatcher;

/**
 *
 * @author justinjohnson14
 */
public final class ArcadeGannon extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.QUEST);

    static{
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                SubType.HUMAN.getPredicate()
        ));
        filter.add(ManaValueLessThanOrEqualToSourcePowerPredicate.instance);
        filter.withMessage("an artifact or Human spell from your graveyard with mana value less than or equal to the number of quest counters on Arcade Gannon.");
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
        Ability ability1 = new CastFromGraveyardOnceEachTurnAbility(filter);
        ability1.withFlavorWord("For Auld Lang Syne");
        this.addAbility(ability1);
    }

    private ArcadeGannon(final ArcadeGannon card) {
        super(card);
    }

    @Override
    public ArcadeGannon copy() {
        return new ArcadeGannon(this);
    }
}
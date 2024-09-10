package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Grath
 */
public final class TekuthalInquiryDominus extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("other artifacts, creatures, and planeswalkers you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public TekuthalInquiryDominus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If you would proliferate, proliferate twice instead.
        this.addAbility(new SimpleStaticAbility(new TekuthalInquiryDominusEffect()));

        // {1}{U/P}{U/P}, Remove three counters from among other artifacts, creatures, and planeswalkers you control: Put an indestructible counter on Tekuthal, Inquiry Dominus.
        Ability ability = new SimpleActivatedAbility(new AddCountersSourceEffect(CounterType.INDESTRUCTIBLE.createInstance()), new ManaCostsImpl<>("{1}{U/P}{U/P}"));
        ability.addCost(new RemoveCounterCost(new TargetPermanent(
                0, Integer.MAX_VALUE,
                filter
        ), null, 3).setText("Remove three counters from among " + filter.getMessage()));
        this.addAbility(ability);
    }

    private TekuthalInquiryDominus(final TekuthalInquiryDominus card) {
        super(card);
    }

    @Override
    public TekuthalInquiryDominus copy() {
        return new TekuthalInquiryDominus(this);
    }
}

class TekuthalInquiryDominusEffect extends ReplacementEffectImpl {

    TekuthalInquiryDominusEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would proliferate, proliferate twice instead.";
    }

    private TekuthalInquiryDominusEffect(final TekuthalInquiryDominusEffect effect) {
        super(effect);
    }

    @Override
    public TekuthalInquiryDominusEffect copy() {
        return new TekuthalInquiryDominusEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PROLIFERATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }
}
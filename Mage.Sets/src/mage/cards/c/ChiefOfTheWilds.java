package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class ChiefOfTheWilds extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.WOLF, "another Wolf you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ChiefOfTheWilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever another Wolf you control enters, put two +1/+1 counters on Chief of the Wilds.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), filter
        ));

        // If an ability of another Wolf or battle you control triggers, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new ChiefOfTheWildsEffect()));
    }

    private ChiefOfTheWilds(final ChiefOfTheWilds card) {
        super(card);
    }

    @Override
    public ChiefOfTheWilds copy() {
        return new ChiefOfTheWilds(this);
    }
}

class ChiefOfTheWildsEffect extends ReplacementEffectImpl {

    ChiefOfTheWildsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if an ability of another Wolf or battle you control triggers, " +
            "that ability triggers an additional time";
    }

    private ChiefOfTheWildsEffect(final ChiefOfTheWildsEffect effect) {
        super(effect);
    }

    @Override
    public ChiefOfTheWildsEffect copy() {
        return new ChiefOfTheWildsEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return permanent != null
            && permanent.isControlledBy(source.getControllerId())
            && (
                permanent.isBattle()
                || (permanent.hasSubtype(SubType.WOLF, game) && !permanent.getId().equals(source.getSourceId()))
            );
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }
}

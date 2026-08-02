package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.watchers.common.AbilityResolvedWatcher;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 * @author muz
 */
public final class BelladonnaTook extends CardImpl {

    public static final FilterPermanent filterToken = new FilterPermanent("a token");

    static {
        filterToken.add(TokenPredicate.TRUE);
    }

    public BelladonnaTook(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a token you control enters, you gain 1 life if this is the first time this ability has resolved this turn. If it's the second time, draw a card. If it's the third time, put a +1/+1 counter on each creature you control.
        Ability ability =new EntersBattlefieldControlledTriggeredAbility(
            new IfAbilityHasResolvedXTimesEffect(
                Outcome.GainLife, 1, new GainLifeEffect(1)
            ).setText("you gain 1 life if this is the first time this ability has resolved this turn"),
            filterToken
        );
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(
            Outcome.DrawCard, 2, new DrawCardSourceControllerEffect(1)
        ).setText("If it's the second time, draw a card"));
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(
            Outcome.BoostCreature, 3, new AddCountersAllEffect(CounterType.P1P1.createInstance(), new FilterControlledCreaturePermanent())
        ).setText("If it's the third time, put a +1/+1 counter on each creature you control"));
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private BelladonnaTook(final BelladonnaTook card) {
        super(card);
    }

    @Override
    public BelladonnaTook copy() {
        return new BelladonnaTook(this);
    }
}

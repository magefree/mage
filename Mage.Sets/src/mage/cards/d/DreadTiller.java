package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PutCardFromHandOrGraveyardOntoBattlefieldEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class DreadTiller extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a creature with a -1/-1 counter on it");

    static {
        filter.add(CounterType.M1M1.getPredicate());
    }

    public DreadTiller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}{G}");

        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When this creature enters, put a -1/-1 counter on target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Whenever a creature with a -1/-1 counter on it dies, you may put a land card from your hand or graveyard onto the battlefield tapped.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new PutCardFromHandOrGraveyardOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A, true), true, filter
        ));
    }

    private DreadTiller(final DreadTiller card) {
        super(card);
    }

    @Override
    public DreadTiller copy() {
        return new DreadTiller(this);
    }
}

package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCardInExile;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BladeOfTheSwarm extends CardImpl {

    private static final FilterCard filter = new FilterCard("exiled card with warp");

    static {
        filter.add(new AbilityPredicate(WarpAbility.class));
    }

    public BladeOfTheSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When this creature enters, choose one --
        // * Put two +1/+1 counters on this creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2))
        );

        // * Put target exiled card with warp on the bottom of its owner's library.
        ability.addMode(new Mode(new PutOnLibraryTargetEffect(false))
                .addTarget(new TargetCardInExile(filter)));
        this.addAbility(ability);
    }

    private BladeOfTheSwarm(final BladeOfTheSwarm card) {
        super(card);
    }

    @Override
    public BladeOfTheSwarm copy() {
        return new BladeOfTheSwarm(this);
    }
}

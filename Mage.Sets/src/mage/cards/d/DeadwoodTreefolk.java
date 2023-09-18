
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldOrLeavesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VanishingSacrificeAbility;
import mage.abilities.keyword.VanishingUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class DeadwoodTreefolk extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("another target creature card from your graveyard");
    static {
        filter.add(AnotherPredicate.instance);
    }

    public DeadwoodTreefolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Vanishing 3
        Ability ability = new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.TIME.createInstance(3)));
        ability.setRuleVisible(false);
        this.addAbility(ability);
        this.addAbility(new VanishingUpkeepAbility(3));
        this.addAbility(new VanishingSacrificeAbility());
        // When Deadwood Treefolk enters the battlefield or leaves the battlefield, return another target creature card from your graveyard to your hand.
        ability = new EntersBattlefieldOrLeavesSourceTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), false);
        Target target = new TargetCardInYourGraveyard(filter);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private DeadwoodTreefolk(final DeadwoodTreefolk card) {
        super(card);
    }

    @Override
    public DeadwoodTreefolk copy() {
        return new DeadwoodTreefolk(this);
    }
}

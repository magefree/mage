package mage.cards.g;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoblinBirdGrabber extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("you control a creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public GoblinBirdGrabber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {R}: Goblin Bird-Grabber gains flying until end of turn. Activate this ability only if you control a creature with flying.
        this.addAbility(new ConditionalActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl("{R}"), condition));
    }

    private GoblinBirdGrabber(final GoblinBirdGrabber card) {
        super(card);
    }

    @Override
    public GoblinBirdGrabber copy() {
        return new GoblinBirdGrabber(this);
    }
}

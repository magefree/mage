package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class WatcherOfTheSpheres extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("Creature spells with flying");
    private static final FilterPermanent filter1 = new FilterControlledCreaturePermanent("another creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter1.add(AnotherPredicate.instance);
        filter1.add(new AbilityPredicate(FlyingAbility.class));
    }

    public WatcherOfTheSpheres(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Creature spells with flying you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever another creature with flying enters the battlefield under your control, Watcher of the Spheres gets +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn), filter1, false));
    }

    private WatcherOfTheSpheres(final WatcherOfTheSpheres card) {
        super(card);
    }

    @Override
    public WatcherOfTheSpheres copy() {
        return new WatcherOfTheSpheres(this);
    }
}

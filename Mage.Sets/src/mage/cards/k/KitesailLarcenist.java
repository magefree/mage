package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.TreasureToken;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KitesailLarcenist extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("other target artifact or creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public KitesailLarcenist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {1}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{1}"), false));

        // When Kitesail Larcenist enters the battlefield, for each player, choose up to one other target artifact or creature that player controls. For as long as Kitesail Larcenist remains on the battlefield, the chosen permanents become Treasure artifacts with "{T}, Sacrifice this artifact: Add one mana of any color" and lose all other abilities.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BecomesCreatureTargetEffect(
                new TreasureToken(), true, false,
                Duration.UntilSourceLeavesBattlefield,
                false, false, true
        ).setTargetPointer(new EachTargetPointer()).setText("for each player, choose up to one other " +
                "target artifact or creature that player controls. For as long as {this} " +
                "remains on the battlefield, the chosen permanents become Treasure artifacts with " +
                "\"{T}, Sacrifice this artifact: Add one mana of any color\" and lose all other abilities"));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        ability.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, false));
        this.addAbility(ability);
    }

    private KitesailLarcenist(final KitesailLarcenist card) {
        super(card);
    }

    @Override
    public KitesailLarcenist copy() {
        return new KitesailLarcenist(this);
    }
}

package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DrawCardsEqualToDifferenceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KrangMasterMind extends CardImpl {

    private static final Condition condition = new CardsInHandCondition(ComparisonType.FEWER_THAN, 4);
    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("other artifact you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public KrangMasterMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.UTROM);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // When Krang enters, if you have fewer than four cards in hand, draw cards equal to the difference.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardsEqualToDifferenceEffect(4))
                .withInterveningIf(condition));

        // Krang gets +1/+0 for each other artifact you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                xValue, StaticValue.get(0), Duration.WhileOnBattlefield
        )));
    }

    private KrangMasterMind(final KrangMasterMind card) {
        super(card);
    }

    @Override
    public KrangMasterMind copy() {
        return new KrangMasterMind(this);
    }
}

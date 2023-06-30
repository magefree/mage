package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.SoldierArtifactToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlatoonDispenser extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("you control two or more other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            filter, ComparisonType.MORE_THAN, 1, true
    );

    public PlatoonDispenser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // At the beginning of your end step, if you control two or more other creatures, draw a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                TargetController.YOU, condition, false
        ));

        // {3}{W}: Create a 1/1 colorless Soldier Artifact creature token.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new SoldierArtifactToken()), new ManaCostsImpl<>("{3}{W}")
        ));

        // Unearth {2}{W}{W}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{2}{W}{W}")));
    }

    private PlatoonDispenser(final PlatoonDispenser card) {
        super(card);
    }

    @Override
    public PlatoonDispenser copy() {
        return new PlatoonDispenser(this);
    }
}

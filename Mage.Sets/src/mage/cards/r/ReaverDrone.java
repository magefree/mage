
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author fireshoes
 */
public final class ReaverDrone extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another colorless creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(ColorlessPredicate.instance);
    }

    public ReaverDrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // At the beginning of your upkeep, you lose 1 life unless you control another colorless creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ConditionalOneShotEffect(
                new LoseLifeSourceControllerEffect(1),
                new InvertCondition(new PermanentsOnTheBattlefieldCondition(filter)),
                "you lose 1 life unless you control another colorless creature"), TargetController.YOU, false));
    }

    private ReaverDrone(final ReaverDrone card) {
        super(card);
    }

    @Override
    public ReaverDrone copy() {
        return new ReaverDrone(this);
    }
}

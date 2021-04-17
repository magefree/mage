
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class FathomFleetBoarder extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("you control another Pirate");

    static {
        filter.add(SubType.PIRATE.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public FathomFleetBoarder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Fathom Fleet Boarder enters the battlefield, you lose 2 life unless you control another Pirate.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new ConditionalOneShotEffect(new LoseLifeSourceControllerEffect(2), new InvertCondition(new PermanentsOnTheBattlefieldCondition(filter)),
                        "you lose 2 life unless you control another Pirate"),
                 false));
    }

    private FathomFleetBoarder(final FathomFleetBoarder card) {
        super(card);
    }

    @Override
    public FathomFleetBoarder copy() {
        return new FathomFleetBoarder(this);
    }
}

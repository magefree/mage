package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.condition.common.CorruptedCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.AbilityWord;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

/**
 * @author TheElk801
 */
public final class ChitteringSkitterling extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("an artifact or creature");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public ChitteringSkitterling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.RAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Corrupted -- Sacrifice an artifact or creature: Draw a card. Activate only if an opponent has three or more poison counters and only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1),
                new SacrificeTargetCost(filter), 1, CorruptedCondition.instance
        ).setAbilityWord(AbilityWord.CORRUPTED).addHint(CorruptedCondition.getHint()));
    }

    private ChitteringSkitterling(final ChitteringSkitterling card) {
        super(card);
    }

    @Override
    public ChitteringSkitterling copy() {
        return new ChitteringSkitterling(this);
    }
}

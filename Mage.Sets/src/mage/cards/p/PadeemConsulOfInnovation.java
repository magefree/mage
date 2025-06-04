package mage.cards.p;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PadeemConsulOfInnovation extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent(
            "you control the artifact with the greatest mana value or tied for the greatest mana value"
    );

    static {
        filter.add(PadeemConsulOfInnovationPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition);

    public PadeemConsulOfInnovation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Artifacts you control have hexproof.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_ARTIFACTS, false
        )));

        // At the beginning of your upkeep, if you control the artifact with the highest converted mana cost or tied for the highest converted mana cost, draw a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DrawCardSourceControllerEffect(1)).withInterveningIf(condition).addHint(hint));
    }

    private PadeemConsulOfInnovation(final PadeemConsulOfInnovation card) {
        super(card);
    }

    @Override
    public PadeemConsulOfInnovation copy() {
        return new PadeemConsulOfInnovation(this);
    }
}

enum PadeemConsulOfInnovationPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getManaValue() >= game
                .getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_PERMANENT_ARTIFACT, input.getControllerId(), game)
                .stream()
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(-1);
    }
}

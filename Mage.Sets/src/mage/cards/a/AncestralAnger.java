package mage.cards.a;

import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AncestralAnger extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new NamePredicate("Ancestral Anger"));
    }

    private static final DynamicValue xValue = new AdditiveDynamicValue(
            new CardsInControllerGraveyardCount(filter), StaticValue.get(1)
    );
    private static final Hint hint = new ValueHint(
            "Cards named Ancestral Anger in your graveyard", new CardsInControllerGraveyardCount(filter)
    );

    public AncestralAnger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Target creature gains trample and gets +X/+0 until end of turn, where X is 1 plus the number of cards named Ancestral Anger in your graveyard.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("target creature gains trample"));
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                xValue, StaticValue.get(0), Duration.EndOfTurn
        ).setText("and gets +X/+0 until end of turn, where X is 1 plus " +
                "the number of cards named Ancestral Anger in your graveyard"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(hint);

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private AncestralAnger(final AncestralAnger card) {
        super(card);
    }

    @Override
    public AncestralAnger copy() {
        return new AncestralAnger(this);
    }
}

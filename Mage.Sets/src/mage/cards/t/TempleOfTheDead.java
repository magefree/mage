package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TempleOfTheDead extends CardImpl {

    private static final Condition condition = new CardsInHandCondition(ComparisonType.FEWER_THAN, 2, TargetController.ANY);
    private static final Hint hint = new ConditionHint(condition, "a player has one or fewer cards in hand");

    public TempleOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.nightCard = true;

        // (Transforms from Aclazotz, Deepest Betrayal.)

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {2}{B}, {T}: Transform Temple of the Dead. Activate only if a player has one or fewer cards in hand and only as a sorcery.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new TransformSourceEffect(),
                new ManaCostsImpl<>("{2}{B}"),
                condition,
                TimingRule.SORCERY
        );
        ability.addCost(new TapSourceCost());
        ability.addHint(hint);
        this.addAbility(ability);
    }

    private TempleOfTheDead(final TempleOfTheDead card) {
        super(card);
    }

    @Override
    public TempleOfTheDead copy() {
        return new TempleOfTheDead(this);
    }
}

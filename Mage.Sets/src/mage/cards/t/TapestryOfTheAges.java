package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.CastNoncreatureSpellThisTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TapestryOfTheAges extends CardImpl {

    public TapestryOfTheAges(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {2}, {T}: Draw a card. Activate this ability only if you've cast a noncreature spell this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new DrawCardSourceControllerEffect(1),
                new GenericManaCost(2), CastNoncreatureSpellThisTurnCondition.instance
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.addHint(CastNoncreatureSpellThisTurnCondition.getHint()));
    }

    private TapestryOfTheAges(final TapestryOfTheAges card) {
        super(card);
    }

    @Override
    public TapestryOfTheAges copy() {
        return new TapestryOfTheAges(this);
    }
}

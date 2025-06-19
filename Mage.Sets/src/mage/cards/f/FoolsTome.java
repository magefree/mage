package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class FoolsTome extends CardImpl {

    public FoolsTome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {2}, {tap}: Draw a card. Activate this ability only if you have no cards in hand.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(2), HellbentCondition.instance
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private FoolsTome(final FoolsTome card) {
        super(card);
    }

    @Override
    public FoolsTome copy() {
        return new FoolsTome(this);
    }
}

package mage.cards.w;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WizardsRockets extends CardImpl {

    public WizardsRockets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // Wizard's Rockets enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {X}, {T}, Sacrifice Wizard's Rockets: Add X mana in any combination of colors.
        Ability ability = new DynamicManaAbility(
                Mana.AnyMana(1), ManacostVariableValue.REGULAR,
                new ManaCostsImpl<>("{X}"), "Add X mana in any combination of colors."
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // When Wizard's Rockets is put into a graveyard from the battlefield, draw a card.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private WizardsRockets(final WizardsRockets card) {
        super(card);
    }

    @Override
    public WizardsRockets copy() {
        return new WizardsRockets(this);
    }
}

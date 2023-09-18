package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnwillingIngredient extends CardImpl {

    public UnwillingIngredient(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.FROG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // {2}{B}, Exile Unwilling Ingredient from your graveyard: You draw a card and you lose 1 life.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new DrawCardSourceControllerEffect(1)
                        .setText("you draw a card"),
                new ManaCostsImpl<>("{2}{B}")
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private UnwillingIngredient(final UnwillingIngredient card) {
        super(card);
    }

    @Override
    public UnwillingIngredient copy() {
        return new UnwillingIngredient(this);
    }
}

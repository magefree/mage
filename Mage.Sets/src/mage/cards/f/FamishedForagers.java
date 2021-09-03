package mage.cards.f;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.OpponentsLostLifeCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.hint.common.OpponentsLostLifeHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FamishedForagers extends CardImpl {

    public FamishedForagers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Famished Foragers enters the battlefield, if an opponent lost life this turn, add {R}{R}{R}.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new BasicManaEffect(Mana.RedMana(3))),
                OpponentsLostLifeCondition.instance, "When {this} enters the battlefield, " +
                "if an opponent lost life this turn, add {R}{R}{R}."
        ).addHint(OpponentsLostLifeHint.instance));

        // {2}{R}, Discard a card: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{2}{R}")
        );
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private FamishedForagers(final FamishedForagers card) {
        super(card);
    }

    @Override
    public FamishedForagers copy() {
        return new FamishedForagers(this);
    }
}

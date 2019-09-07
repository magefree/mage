package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SavvyHunter extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.FOOD, "Foods");

    public SavvyHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Savvy Hunter attacks or blocks, create a Food token.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new CreateTokenEffect(new FoodToken()), false));

        // Sacrifice two Foods: Draw a card.
        this.addAbility(new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1),
                new SacrificeTargetCost(new TargetControlledPermanent(2, filter))
        ));
    }

    private SavvyHunter(final SavvyHunter card) {
        super(card);
    }

    @Override
    public SavvyHunter copy() {
        return new SavvyHunter(this);
    }
}

package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EyeOfVecna extends CardImpl {

    public EyeOfVecna(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Eye of Vecna enters the battlefield, you draw a card and you lose 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(1).setText("you draw a card")
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(2).concatBy("and"));
        this.addAbility(ability);

        // At the beginning of your upkeep, you may pay {2}. If you do, you draw a card and you lose 2 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DoIfCostPaid(
                        new DrawCardSourceControllerEffect(1)
                                .setText("you draw a card"),
                        new GenericManaCost(2)
                ).addEffect(new LoseLifeSourceControllerEffect(2).concatBy("and")),
                TargetController.YOU, false
        ));
    }

    private EyeOfVecna(final EyeOfVecna card) {
        super(card);
    }

    @Override
    public EyeOfVecna copy() {
        return new EyeOfVecna(this);
    }
}

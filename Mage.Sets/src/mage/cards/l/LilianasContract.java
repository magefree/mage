package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LilianasContract extends CardImpl {

    public LilianasContract(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // When Liliana's Contract enters the battlefield, you draw four cards and you lose 4 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(4)
                        .setText("you draw four cards")
        );
        ability.addEffect(
                new LoseLifeSourceControllerEffect(4)
                        .setText("and you lose 4 life")
        );
        this.addAbility(ability);

        // At the beginning of your upkeep, if you control four or more Demons with different names, you win the game.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        new WinGameSourceControllerEffect(),
                        TargetController.YOU, false
                ), LilianasContractCondition.instance,
                "At the beginning of your upkeep, "
                        + "if you control four or more Demons with different names, "
                        + "you win the game."
        ));
    }

    private LilianasContract(final LilianasContract card) {
        super(card);
    }

    @Override
    public LilianasContract copy() {
        return new LilianasContract(this);
    }
}

enum LilianasContractCondition implements Condition {
    instance;
    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DEMON);

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.differentlyNamedAmongCollection(
                game.getBattlefield().getActivePermanents(
                        filter, source.getControllerId(), source, game
                ), game
        ) >= 4;
    }

    @Override
    public String toString() {
        return "you control four or more Demons with different names";
    }
}

package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.DifferentlyNamedPermanentCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LilianasContract extends CardImpl {

    public LilianasContract(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // When Liliana's Contract enters the battlefield, you draw four cards and you lose 4 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(4, true));
        ability.addEffect(new LoseLifeSourceControllerEffect(4).setText("and you lose 4 life"));
        this.addAbility(ability);

        // At the beginning of your upkeep, if you control four or more Demons with different names, you win the game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect())
                .withInterveningIf(LilianasContractCondition.instance).addHint(LilianasContractCondition.getHint()));
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
    private static final DifferentlyNamedPermanentCount xValue = new DifferentlyNamedPermanentCount(
            new FilterControlledPermanent(SubType.DEMON, "Demons you control")
    );

    static Hint getHint() {
        return xValue.getHint();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return xValue.calculate(game, source, null) >= 4;
    }

    @Override
    public String toString() {
        return "you control four or more Demons with different names";
    }
}

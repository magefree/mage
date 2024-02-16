package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.game.permanent.token.ServoToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

import mage.filter.StaticFilters;

/**
 * @author LevelX2
 */
public final class HiddenStockpile extends CardImpl {

    public HiddenStockpile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{B}");

        // <i>Revolt</i> &mdash; At the beginning of your end step, if a permanent you controlled left the battlefield this turn, create a 1/1 colorless Servo artifact creature token.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfYourEndStepTriggeredAbility(new CreateTokenEffect(new ServoToken()), false),
                RevoltCondition.instance, "At the beginning of your end step, if a permanent you controlled " +
                "left the battlefield this turn, create a 1/1 colorless Servo artifact creature token."
        ).setAbilityWord(AbilityWord.REVOLT), new RevoltWatcher());

        // {1}, Sacrifice a creature: Scry 1.
        Ability ability = new SimpleActivatedAbility(new ScryEffect(1, false), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.addAbility(ability);
    }

    private HiddenStockpile(final HiddenStockpile card) {
        super(card);
    }

    @Override
    public HiddenStockpile copy() {
        return new HiddenStockpile(this);
    }
}

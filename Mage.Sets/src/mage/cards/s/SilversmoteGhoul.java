package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilversmoteGhoul extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 2);
    private static final Hint hint = new ConditionHint(condition);

    public SilversmoteGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // At the beginning of your end step, if you gained 3 or more life this turn, return Silversmote Ghoul from your graveyard to the battlefield tapped.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.GRAVEYARD, TargetController.YOU,
                new ReturnSourceFromGraveyardToBattlefieldEffect(true),
                false, condition
        ).addHint(hint), new PlayerGainedLifeWatcher());

        // {1}{B}, Sacrifice Silversmote Ghoul: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private SilversmoteGhoul(final SilversmoteGhoul card) {
        super(card);
    }

    @Override
    public SilversmoteGhoul copy() {
        return new SilversmoteGhoul(this);
    }
}

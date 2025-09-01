package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldWithCounterEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BalustradeWurm extends CardImpl {

    public BalustradeWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.WURM);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Delirium--{2}{G}{G}: Return Balustrade Wurm from your graveyard to the battlefield with a finality counter on it. Activate only if there are four or more card types among cards in your graveyard and only as a sorcery.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(CounterType.FINALITY.createInstance(), false),
                new ManaCostsImpl<>("{2}{G}{G}"), DeliriumCondition.instance
        ).setTiming(TimingRule.SORCERY).setAbilityWord(AbilityWord.DELIRIUM).addHint(CardTypesInGraveyardCount.YOU.getHint()));
    }

    private BalustradeWurm(final BalustradeWurm card) {
        super(card);
    }

    @Override
    public BalustradeWurm copy() {
        return new BalustradeWurm(this);
    }
}

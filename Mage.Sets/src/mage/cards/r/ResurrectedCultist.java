package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldWithCounterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ResurrectedCultist extends CardImpl {

    public ResurrectedCultist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Delirium -- {2}{B}{B}: Return Resurrected Cultist from your graveyard to the battlefield with a finality counter on it. Activate only if there are four or more card types among cards in your graveyard and only as a sorcery.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(CounterType.FINALITY.createInstance(), false),
                new ManaCostsImpl<>("{2}{B}{B}"), DeliriumCondition.instance
        ).setTiming(TimingRule.SORCERY).setAbilityWord(AbilityWord.DELIRIUM).addHint(CardTypesInGraveyardCount.YOU.getHint()));
    }

    private ResurrectedCultist(final ResurrectedCultist card) {
        super(card);
    }

    @Override
    public ResurrectedCultist copy() {
        return new ResurrectedCultist(this);
    }
}

package mage.cards.s;

import mage.MageInt;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class StallionOfAshmouth extends CardImpl {

    public StallionOfAshmouth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // <i>Delirium</i> &mdash; {1}{B}: Stallion of Ashmouth gets +1/+1 until end of turn. Activate this ability only if there are
        // four or more card types among cards in your graveyard.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{B}"), DeliriumCondition.instance
        ).setAbilityWord(AbilityWord.DELIRIUM).addHint(CardTypesInGraveyardCount.YOU.getHint()));
    }

    private StallionOfAshmouth(final StallionOfAshmouth card) {
        super(card);
    }

    @Override
    public StallionOfAshmouth copy() {
        return new StallionOfAshmouth(this);
    }
}

package mage.cards.s;

import mage.MageInt;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

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
        this.addAbility(new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{B}"), DeliriumCondition.instance
        ).setAbilityWord(AbilityWord.DELIRIUM).addHint(CardTypesInGraveyardHint.YOU));
    }

    private StallionOfAshmouth(final StallionOfAshmouth card) {
        super(card);
    }

    @Override
    public StallionOfAshmouth copy() {
        return new StallionOfAshmouth(this);
    }
}

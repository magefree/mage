package mage.cards.o;

import mage.MageInt;
import mage.abilities.abilityword.CohortAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class OnduWarCleric extends CardImpl {

    public OnduWarCleric(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Cohort</i> &mdash; {T}, Tap an untapped Ally you control: You gain 2 life.
        this.addAbility(new CohortAbility(new GainLifeEffect(2)));
    }

    private OnduWarCleric(final OnduWarCleric card) {
        super(card);
    }

    @Override
    public OnduWarCleric copy() {
        return new OnduWarCleric(this);
    }
}

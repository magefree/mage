package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.CohortAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class MalakirSoothsayer extends CardImpl {

    public MalakirSoothsayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SHAMAN);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // <i>Cohort</i> &mdash; {T}, Tap an untapped Ally you control: You draw a card and you lose a life.
        Ability ability = new CohortAbility(new DrawCardSourceControllerEffect(1, "you"));
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);

    }

    private MalakirSoothsayer(final MalakirSoothsayer card) {
        super(card);
    }

    @Override
    public MalakirSoothsayer copy() {
        return new MalakirSoothsayer(this);
    }
}

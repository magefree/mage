package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.CohortAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ZulaportChainmage extends CardImpl {

    public ZulaportChainmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.HUMAN, SubType.SHAMAN, SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // <i>Cohort</i> &mdash; {T}, Tap an untapped Ally you control: Target opponent loses 2 life.
        Ability ability = new CohortAbility(new LoseLifeTargetEffect(2));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private ZulaportChainmage(final ZulaportChainmage card) {
        super(card);
    }

    @Override
    public ZulaportChainmage copy() {
        return new ZulaportChainmage(this);
    }
}

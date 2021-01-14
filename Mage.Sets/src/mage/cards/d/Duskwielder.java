package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.BoastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Duskwielder extends CardImpl {

    public Duskwielder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Boast — {1}: Target opponent loses 1 life and you gain 1 life.
        Ability ability = new BoastAbility(new LoseLifeTargetEffect(1), new GenericManaCost(1));
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private Duskwielder(final Duskwielder card) {
        super(card);
    }

    @Override
    public Duskwielder copy() {
        return new Duskwielder(this);
    }
}

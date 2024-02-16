package mage.cards.s;

import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SliceAndDice extends CardImpl {

    public SliceAndDice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Slice and Dice deals 4 damage to each creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(4, new FilterCreaturePermanent()));
        // Cycling {2}{R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{R}")));
        // When you cycle Slice and Dice, you may have it deal 1 damage to each creature.
        this.addAbility(new CycleTriggeredAbility(new DamageAllEffect(1, StaticFilters.FILTER_PERMANENT_CREATURE).setText("have it deal 1 damage to each creature"), true));
    }

    private SliceAndDice(final SliceAndDice card) {
        super(card);
    }

    @Override
    public SliceAndDice copy() {
        return new SliceAndDice(this);
    }
}

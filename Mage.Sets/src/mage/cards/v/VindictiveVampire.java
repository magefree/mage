package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VindictiveVampire extends CardImpl {

    public VindictiveVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever another creature you control dies, Vindictive Vampire deals 1 damage to each opponent and you gain 1 life.
        Ability ability = new DiesCreatureTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT), false,
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, true
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private VindictiveVampire(final VindictiveVampire card) {
        super(card);
    }

    @Override
    public VindictiveVampire copy() {
        return new VindictiveVampire(this);
    }
}

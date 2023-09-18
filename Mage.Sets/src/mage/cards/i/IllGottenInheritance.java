package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IllGottenInheritance extends CardImpl {

    public IllGottenInheritance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // At the beginning of your upkeep, Ill-Gotten Inheritance deals 1 damage to each opponent and you gain 1 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT),
                TargetController.YOU, false
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);

        // {5}{B}, Sacrifice Ill-Gotten Inheritance: It deals 4 damage to target opponent and you gain 4 life.
        ability = new SimpleActivatedAbility(
                new DamageTargetEffect(4, "it"),
                new ManaCostsImpl<>("{5}{B}")
        );
        ability.addEffect(new GainLifeEffect(4).concatBy("and"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private IllGottenInheritance(final IllGottenInheritance card) {
        super(card);
    }

    @Override
    public IllGottenInheritance copy() {
        return new IllGottenInheritance(this);
    }
}

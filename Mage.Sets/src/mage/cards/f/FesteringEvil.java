package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class FesteringEvil extends CardImpl {

    public FesteringEvil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // At the beginning of your upkeep, Festering Evil deals 1 damage to each creature and each player.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DamageEverythingEffect(1), TargetController.YOU, false
        ));

        // {B}{B}, Sacrifice Festering Evil: Festering Evil deals 3 damage to each creature and each player.
        Ability ability = new SimpleActivatedAbility(
                new DamageEverythingEffect(3, "it"), new ManaCostsImpl<>("{B}{B}")
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private FesteringEvil(final FesteringEvil card) {
        super(card);
    }

    @Override
    public FesteringEvil copy() {
        return new FesteringEvil(this);
    }
}

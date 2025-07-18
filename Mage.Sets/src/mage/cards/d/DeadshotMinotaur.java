
package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author jonubuu
 */
public final class DeadshotMinotaur extends CardImpl {

    public DeadshotMinotaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");
        this.subtype.add(SubType.MINOTAUR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Deadshot Minotaur enters the battlefield, it deals 3 damage to target creature with flying.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3, "it"), false);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.addAbility(ability);

        // Cycling {RG}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{R/G}")));
    }

    private DeadshotMinotaur(final DeadshotMinotaur card) {
        super(card);
    }

    @Override
    public DeadshotMinotaur copy() {
        return new DeadshotMinotaur(this);
    }
}

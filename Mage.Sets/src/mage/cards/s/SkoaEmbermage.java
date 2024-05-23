package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.GrandeurAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkoaEmbermage extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.MOUNTAIN, "Mountains");

    public SkoaEmbermage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Skoa, Embermage enters the battlefield, deals 4 damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(4, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Grandeur -- Discard another card named Skoa, Embermage, Sacrifice two Mountains: Skoa deals 4 damage to any target.
        ability = new GrandeurAbility(new DamageTargetEffect(4), "Skoa, Embermage");
        ability.addCost(new SacrificeTargetCost(2, filter));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SkoaEmbermage(final SkoaEmbermage card) {
        super(card);
    }

    @Override
    public SkoaEmbermage copy() {
        return new SkoaEmbermage(this);
    }
}

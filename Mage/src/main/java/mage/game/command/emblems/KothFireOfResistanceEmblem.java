package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.command.Emblem;
import mage.target.common.TargetAnyTarget;

/**
 * @author PurpleCrowbar
 */
public final class KothFireOfResistanceEmblem extends Emblem {

    private static final FilterPermanent filterMountain = new FilterPermanent("a Mountain");

    static {
        filterMountain.add(SubType.MOUNTAIN.getPredicate());
    }

    // −7: You get an emblem with "Whenever a Mountain enters the battlefield under your control, this emblem deals 4 damage to any target."
    public KothFireOfResistanceEmblem() {
        super("Emblem Koth");

        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                Zone.COMMAND, new DamageTargetEffect(4).setText("this emblem deals 4 damage to any target"),
                filterMountain, false);
        ability.addTarget(new TargetAnyTarget());
        getAbilities().add(ability);
    }

    private KothFireOfResistanceEmblem(final KothFireOfResistanceEmblem card) {
        super(card);
    }

    @Override
    public KothFireOfResistanceEmblem copy() {
        return new KothFireOfResistanceEmblem(this);
    }
}

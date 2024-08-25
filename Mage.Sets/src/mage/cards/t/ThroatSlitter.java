
package mage.cards.t;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class ThroatSlitter extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("nonblack creature that player controls");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public ThroatSlitter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.NINJA);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Ninjutsu {2}{B} ({2}{B}, Return an unblocked attacker you control to hand: Put this card onto the battlefield from your hand tapped and attacking.)
        this.addAbility(new NinjutsuAbility("{2}{B}"));

        // Whenever Throat Slitter deals combat damage to a player, destroy target nonblack creature that player controls.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DestroyTargetEffect(), false, true);
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private ThroatSlitter(final ThroatSlitter card) {
        super(card);
    }

    @Override
    public ThroatSlitter copy() {
        return new ThroatSlitter(this);
    }
}

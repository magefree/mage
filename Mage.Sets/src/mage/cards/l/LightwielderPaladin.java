
package mage.cards.l;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class LightwielderPaladin extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("black or red permanent that player controls");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.BLACK),
                new ColorPredicate(ObjectColor.RED)));
    }

    public LightwielderPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        //First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Lightwielder Paladin deals combat damage to a player, you may exile target black or red permanent that player controls.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new ExileTargetEffect(), true, true);
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private LightwielderPaladin(final LightwielderPaladin card) {
        super(card);
    }

    @Override
    public LightwielderPaladin copy() {
        return new LightwielderPaladin(this);
    }
}

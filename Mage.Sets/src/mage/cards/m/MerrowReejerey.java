
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author jonubuu
 */
public final class MerrowReejerey extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("Merfolk creatures you control");
    private static final FilterSpell filter2 = new FilterSpell("a Merfolk spell");

    static {
        filter1.add(SubType.MERFOLK.getPredicate());
        filter1.add(TargetController.YOU.getControllerPredicate());
        filter2.add(SubType.MERFOLK.getPredicate());
    }

    public MerrowReejerey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Merfolk creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter1, true)));
        // Whenever you cast a Merfolk spell, you may tap or untap target permanent.
        Ability ability = new SpellCastControllerTriggeredAbility(new MayTapOrUntapTargetEffect(), filter2, true);
        Target target = new TargetPermanent();
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private MerrowReejerey(final MerrowReejerey card) {
        super(card);
    }

    @Override
    public MerrowReejerey copy() {
        return new MerrowReejerey(this);
    }
}

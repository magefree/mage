
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MeleeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class AdrianaCaptainOfTheGuard extends CardImpl {

    public AdrianaCaptainOfTheGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Melee
        this.addAbility(new MeleeAbility());

        // Other creatures you control have melee.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(new MeleeAbility(), Duration.WhileOnBattlefield, new FilterControlledCreaturePermanent("creatures"), true)));

    }

    private AdrianaCaptainOfTheGuard(final AdrianaCaptainOfTheGuard card) {
        super(card);
    }

    @Override
    public AdrianaCaptainOfTheGuard copy() {
        return new AdrianaCaptainOfTheGuard(this);
    }
}


package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class ArmaggonFutureShark extends CardImpl {

    public ArmaggonFutureShark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHARK);
        this.subtype.add(SubType.HORROR);
        this.subtype.add(SubType.MUTANT);

        this.power = new MageInt(9);
        this.toughness = new MageInt(6);

        this.addAbility(FlashAbility.getInstance());

        // When Armaggon enters, destroy up to three target creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 3));
        this.addAbility(ability);
    }

    private ArmaggonFutureShark(final ArmaggonFutureShark card) {
        super(card);
    }

    @Override
    public ArmaggonFutureShark copy() {
        return new ArmaggonFutureShark(this);
    }
}

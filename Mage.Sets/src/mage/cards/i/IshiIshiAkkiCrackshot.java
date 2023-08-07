
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterSpiritOrArcaneCard;

/**
 *
 * @author LevelX2
 */
public final class IshiIshiAkkiCrackshot extends CardImpl {

    private static final FilterSpiritOrArcaneCard filter = new FilterSpiritOrArcaneCard();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public IshiIshiAkkiCrackshot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever an opponent casts a Spirit or Arcane spell, Ishi-Ishi, Akki Crackshot deals 2 damage to that player.
        this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2, true, "that player"), filter, false, SetTargetPointer.PLAYER));
    }

    private IshiIshiAkkiCrackshot(final IshiIshiAkkiCrackshot card) {
        super(card);
    }

    @Override
    public IshiIshiAkkiCrackshot copy() {
        return new IshiIshiAkkiCrackshot(this);
    }
}

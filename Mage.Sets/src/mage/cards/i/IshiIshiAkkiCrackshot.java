
package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class IshiIshiAkkiCrackshot extends CardImpl {

    public IshiIshiAkkiCrackshot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever an opponent casts a Spirit or Arcane spell, Ishi-Ishi, Akki Crackshot deals 2 damage to that player.
        this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD,
                new DamageTargetEffect(2, true, "that player"),
                StaticFilters.FILTER_SPELL_SPIRIT_OR_ARCANE, false, SetTargetPointer.PLAYER));
    }

    private IshiIshiAkkiCrackshot(final IshiIshiAkkiCrackshot card) {
        super(card);
    }

    @Override
    public IshiIshiAkkiCrackshot copy() {
        return new IshiIshiAkkiCrackshot(this);
    }
}

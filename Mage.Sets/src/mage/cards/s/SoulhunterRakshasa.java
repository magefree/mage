package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class SoulhunterRakshasa extends CardImpl {

    public SoulhunterRakshasa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.CAT, SubType.DEMON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Soulhunter Rakshasa can’t block.
        this.addAbility(new CantBlockAbility());

        // When Soulhunter Rakshasa enters the battlefield, it deals 5 damage to target opponent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(5, "it"), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private SoulhunterRakshasa(final SoulhunterRakshasa card) {
        super(card);
    }

    @Override
    public SoulhunterRakshasa copy() {
        return new SoulhunterRakshasa(this);
    }
}

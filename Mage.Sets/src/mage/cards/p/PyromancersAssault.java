package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class PyromancersAssault extends CardImpl {

    public PyromancersAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // Whenever you cast your second spell each turn, Pyromancer's Assault deals 2 damage to any target.
        Ability ability = new CastSecondSpellTriggeredAbility(new DamageTargetEffect(2));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private PyromancersAssault(final PyromancersAssault card) {
        super(card);
    }

    @Override
    public PyromancersAssault copy() {
        return new PyromancersAssault(this);
    }
}

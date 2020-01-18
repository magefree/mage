package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.FirstSpellOpponentsTurnTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DreamstalkerManticore extends CardImpl {

    public DreamstalkerManticore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.MANTICORE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever you cast your first spell during each opponent's turn, Dreamstalker Manticore deals 1 damage to any target.
        Ability ability = new FirstSpellOpponentsTurnTriggeredAbility(
                new DamageTargetEffect(1), false
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private DreamstalkerManticore(final DreamstalkerManticore card) {
        super(card);
    }

    @Override
    public DreamstalkerManticore copy() {
        return new DreamstalkerManticore(this);
    }
}

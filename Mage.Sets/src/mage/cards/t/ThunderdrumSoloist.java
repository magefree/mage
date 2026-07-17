package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.abilities.abilityword.OpusAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ThunderdrumSoloist extends CardImpl {

    public ThunderdrumSoloist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Opus -- Whenever you cast an instant or sorcery spell, this creature deals 1 damage to each opponent. If five or more mana was spent to cast that spell, this creature deals 3 damage to each opponent instead.
        this.addAbility(new OpusAbility(
            new DamagePlayersEffect(1, TargetController.OPPONENT),
            new DamagePlayersEffect(3, TargetController.OPPONENT),
            "this creature deals 1 damage to each opponent. " +
                "If five or more mana was spent to cast that spell, " +
                "this creature deals 3 damage to each opponent instead",
            true
        ));
    }

    private ThunderdrumSoloist(final ThunderdrumSoloist card) {
        super(card);
    }

    @Override
    public ThunderdrumSoloist copy() {
        return new ThunderdrumSoloist(this);
    }
}

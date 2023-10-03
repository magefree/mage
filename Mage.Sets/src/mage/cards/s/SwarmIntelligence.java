
package mage.cards.s;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.common.FilterInstantOrSorcerySpell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SwarmIntelligence extends CardImpl {

    public SwarmIntelligence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{6}{U}");

        // Whenever you cast an instant or sorcery spell, you may copy that spell. You may choose new targets for the copy.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CopyTargetSpellEffect(true)
                        .setText("you may copy that spell. You may choose new targets for the copy"),
                new FilterInstantOrSorcerySpell("an instant or sorcery spell"),
                true, SetTargetPointer.SPELL
        ));
    }

    private SwarmIntelligence(final SwarmIntelligence card) {
        super(card);
    }

    @Override
    public SwarmIntelligence copy() {
        return new SwarmIntelligence(this);
    }
}

package mage.cards.c;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MulticoloredPredicate;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ClovenCasting extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a multicolored instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
        filter.add(MulticoloredPredicate.instance);
    }

    public ClovenCasting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{U}{R}");

        // Whenever you cast a multicolored instant or sorcery spell, you may pay {1}. If you do, copy that spell. You may choose new targets for the copy.
        Effect effect = new CopyTargetSpellEffect(true);
        effect.setText("copy that spell. You may choose new targets for the copy");
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DoIfCostPaid(effect, new GenericManaCost(1)),
                filter, false, SetTargetPointer.SPELL
        ));
    }

    private ClovenCasting(final ClovenCasting card) {
        super(card);
    }

    @Override
    public ClovenCasting copy() {
        return new ClovenCasting(this);
    }
}

package mage.cards.r;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReflectionsOfLittjara extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell of the chosen type");

    static {
        filter.add(ChosenSubtypePredicate.TRUE);
    }

    public ReflectionsOfLittjara(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");

        // As Reflections of Littjara enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Neutral)));

        // Whenever you cast a spell of the chosen type, copy that spell.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CopyTargetSpellEffect(false, true, false)
                        .setText("copy that spell"),
                filter, false, SetTargetPointer.SPELL
        ));
    }

    private ReflectionsOfLittjara(final ReflectionsOfLittjara card) {
        super(card);
    }

    @Override
    public ReflectionsOfLittjara copy() {
        return new ReflectionsOfLittjara(this);
    }
}

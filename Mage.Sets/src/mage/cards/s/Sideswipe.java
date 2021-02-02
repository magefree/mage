
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class Sideswipe extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("Arcane spell");
    static {
        filter.add(SubType.ARCANE.getPredicate());
    }

    public Sideswipe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");


        // You may change any targets of target Arcane spell.
        Effect effect = new ChooseNewTargetsTargetEffect(false, false);
        effect.setText("You may change any targets of target Arcane spell");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private Sideswipe(final Sideswipe card) {
        super(card);
    }

    @Override
    public Sideswipe copy() {
        return new Sideswipe(this);
    }
}

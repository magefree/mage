
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public final class AbruptDecay extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public AbruptDecay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}{G}");

        // Abrupt Decay can't be countered.
        Effect effect = new CantBeCounteredSourceEffect();
        effect.setText("this spell can't be countered");
        Ability ability = new SimpleStaticAbility(Zone.STACK, effect);
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Destroy target nonland permanent with converted mana cost 3 or less.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent(filter));
    }

    private AbruptDecay(final AbruptDecay card) {
        super(card);
    }

    @Override
    public AbruptDecay copy() {
        return new AbruptDecay(this);
    }
}

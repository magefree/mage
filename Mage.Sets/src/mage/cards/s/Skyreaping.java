
package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LevelX2
 */
public final class Skyreaping extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public Skyreaping(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");


        // Skyreaping deals damage to each creature with flying equal to your devotion to green.
        Effect effect = new DamageAllEffect(new DevotionCount(ColoredManaSymbol.G), filter);
        effect.setText("{this} deals damage to each creature with flying equal to your devotion to green <i>(Each {G} in the mana costs of permanents you control counts toward your devotion to green.)</i>");
        this.getSpellAbility().addEffect(effect);
    }

    public Skyreaping(final Skyreaping card) {
        super(card);
    }

    @Override
    public Skyreaping copy() {
        return new Skyreaping(this);
    }
}

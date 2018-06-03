
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.PayMoreToCastAsThoughtItHadFlashAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author LoneFox
 *
 */
public final class SaprolingSymbiosis extends CardImpl {

    public SaprolingSymbiosis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        Effect effect = new CreateTokenEffect(new SaprolingToken(), new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent()));
        // You may cast Saproling Symbiosis as though it had flash if you pay {2} more to cast it.
        Ability ability = new PayMoreToCastAsThoughtItHadFlashAbility(this, new ManaCostsImpl("{2}"));
        ability.addEffect(effect);
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        // Create a 1/1 green Saproling creature token for each creature you control.
        this.getSpellAbility().addEffect(effect);
    }

    public SaprolingSymbiosis(final SaprolingSymbiosis card) {
        super(card);
    }

    @Override
    public SaprolingSymbiosis copy() {
        return new SaprolingSymbiosis(this);
    }
}

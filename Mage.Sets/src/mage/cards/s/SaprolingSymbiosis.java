package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.PayMoreToCastAsThoughtItHadFlashAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SaprolingToken;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class SaprolingSymbiosis extends CardImpl {

    public SaprolingSymbiosis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        Effect effect = new CreateTokenEffect(new SaprolingToken(), new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE));

        // You may cast Saproling Symbiosis as though it had flash if you pay {2} more to cast it. (You may cast it any time you could cast an instant.)
        Ability ability = new PayMoreToCastAsThoughtItHadFlashAbility(this, new ManaCostsImpl<>("{2}"));
        ability.addEffect(effect);
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Create a 1/1 green Saproling creature token for each creature you control.
        this.getSpellAbility().addEffect(effect);
    }

    private SaprolingSymbiosis(final SaprolingSymbiosis card) {
        super(card);
    }

    @Override
    public SaprolingSymbiosis copy() {
        return new SaprolingSymbiosis(this);
    }
}

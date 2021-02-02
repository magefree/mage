
package mage.cards.r;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class RadiantFlames extends CardImpl {

    public RadiantFlames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // Converge — Radiant Flames deals X damage to each creature, where X is the number of colors of mana spent to cast Radiant Flames.
        getSpellAbility().setAbilityWord(AbilityWord.CONVERGE);
        getSpellAbility().addEffect(new DamageAllEffect(ColorsOfManaSpentToCastCount.getInstance(), new FilterCreaturePermanent()));
    }

    private RadiantFlames(final RadiantFlames card) {
        super(card);
    }

    @Override
    public RadiantFlames copy() {
        return new RadiantFlames(this);
    }
}

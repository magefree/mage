
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author LevelX2
 */
public final class FarmMarket extends SplitCard {

    public FarmMarket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{2}{W}", "{2}{U}", SpellAbilityType.SPLIT_AFTERMATH);

        // Destroy target attacking or blocking creature.
        getLeftHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());

        // Market {2}{U}
        // Sorcery
        // Aftermath
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        // Draw two cards, then discard two cards
        getRightHalfCard().getSpellAbility().addEffect(new DrawDiscardControllerEffect(2, 2));

    }

    public FarmMarket(final FarmMarket card) {
        super(card);
    }

    @Override
    public FarmMarket copy() {
        return new FarmMarket(this);
    }
}

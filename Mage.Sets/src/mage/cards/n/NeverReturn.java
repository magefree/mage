
package mage.cards.n;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class NeverReturn extends SplitCard {

    public NeverReturn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, new CardType[]{CardType.SORCERY}, "{1}{B}{B}", "{3}{B}", SpellAbilityType.SPLIT_AFTERMATH);

        // Never
        // Destroy target creature or planeswalker.
        getLeftHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        // Return
        // Exile target card from a graveyard. Create a 2/2 black Zombie creature token.
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        getRightHalfCard().getSpellAbility().addEffect(new ExileTargetEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetCardInGraveyard());
        getRightHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(new ZombieToken()));
    }

    public NeverReturn(final NeverReturn card) {
        super(card);
    }

    @Override
    public NeverReturn copy() {
        return new NeverReturn(this);
    }
}

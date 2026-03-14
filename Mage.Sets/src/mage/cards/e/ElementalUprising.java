
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class ElementalUprising extends CardImpl {

    public ElementalUprising(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Target land you control becomes a 4/4 Elemental creature with haste until end of turn. It's still a land. It must be blocked this turn if able.
        this.getSpellAbility().addEffect(new BecomesCreatureTargetEffect(
            new CreatureToken(
                4, 4,
                "4/4 Elemental creature with haste",
                SubType.ELEMENTAL
            ).withAbility(HasteAbility.getInstance()),
            false, true, Duration.EndOfTurn
        ));
        this.getSpellAbility().addTarget(new TargetPermanent(new FilterControlledLandPermanent()));
        Effect effect = new MustBeBlockedByAtLeastOneTargetEffect(Duration.EndOfTurn);
        effect.setText("It must be blocked this turn if able");
        this.getSpellAbility().addEffect(effect);
    }

    private ElementalUprising(final ElementalUprising card) {
        super(card);
    }

    @Override
    public ElementalUprising copy() {
        return new ElementalUprising(this);
    }
}

package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.FaerieRogueToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class VioletPall extends CardImpl {

    public VioletPall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.INSTANT},"{4}{B}");
        this.subtype.add(SubType.FAERIE);

        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new FaerieRogueToken(), 1));
    }

    private VioletPall(final VioletPall card) {
        super(card);
    }

    @Override
    public VioletPall copy() {
        return new VioletPall(this);
    }
}

package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public final class AetherGale extends CardImpl {

    public AetherGale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{U}");


        // Return six target nonland permanents to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent(6,6, StaticFilters.FILTER_PERMANENTS_NON_LAND, false));
    }

    private AetherGale(final AetherGale card) {
        super(card);
    }

    @Override
    public AetherGale copy() {
        return new AetherGale(this);
    }
}

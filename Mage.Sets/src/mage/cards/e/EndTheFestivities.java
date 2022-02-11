package mage.cards.e;

import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EndTheFestivities extends CardImpl {

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public EndTheFestivities(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // End the Festivities deals 1 damage to each opponent and each creature and planeswalker they control.
        this.getSpellAbility().addEffect(new DamagePlayersEffect(1, TargetController.OPPONENT));
        this.getSpellAbility().addEffect(new DamageAllEffect(1, filter)
                .setText("and each creature and planeswalker they control"));
    }

    private EndTheFestivities(final EndTheFestivities card) {
        super(card);
    }

    @Override
    public EndTheFestivities copy() {
        return new EndTheFestivities(this);
    }
}

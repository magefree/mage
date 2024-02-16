package mage.cards.e;

import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EatToExtinction extends CardImpl {

    public EatToExtinction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Exile target creature or planeswalker. Look at the top card of your library. You may put that card into your graveyard.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addEffect(new SurveilEffect(1));
    }

    private EatToExtinction(final EatToExtinction card) {
        super(card);
    }

    @Override
    public EatToExtinction copy() {
        return new EatToExtinction(this);
    }
}

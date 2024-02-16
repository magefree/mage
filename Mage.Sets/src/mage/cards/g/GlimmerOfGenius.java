package mage.cards.g;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GlimmerOfGenius extends CardImpl {

    public GlimmerOfGenius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Scry 2, then draw two card. You get {E}{E}.
        this.getSpellAbility().addEffect(new ScryEffect(2, false));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2).concatBy(", then"));
        this.getSpellAbility().addEffect(new GetEnergyCountersControllerEffect(2));
    }

    private GlimmerOfGenius(final GlimmerOfGenius card) {
        super(card);
    }

    @Override
    public GlimmerOfGenius copy() {
        return new GlimmerOfGenius(this);
    }
}

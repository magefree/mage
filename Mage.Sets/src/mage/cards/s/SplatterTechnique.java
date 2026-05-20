package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SplatterTechnique extends CardImpl {

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent("creature and planeswalker");

    public SplatterTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{U}{R}{R}");

        // Choose one --
        // * Draw four cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(4));

        // * Splatter Technique deals 4 damage to each creature and planeswalker.
        this.getSpellAbility().addMode(new Mode(new DamageAllEffect(4, filter)));
    }

    private SplatterTechnique(final SplatterTechnique card) {
        super(card);
    }

    @Override
    public SplatterTechnique copy() {
        return new SplatterTechnique(this);
    }
}

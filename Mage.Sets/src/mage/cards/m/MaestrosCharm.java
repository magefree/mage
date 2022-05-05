package mage.cards.m;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaestrosCharm extends CardImpl {

    public MaestrosCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{B}{R}");

        // Choose one —
        // • Look at the top five cards of your library. Put one of those cards into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(5, 1, PutCards.HAND, PutCards.GRAVEYARD)
                .setText("look at the top five cards of your library. " +
                        "Put one of those cards into your hand and the rest into your graveyard"));

        // • Each opponent loses 3 life and you gain 3 life.
        Mode mode = new Mode(new LoseLifeOpponentsEffect(3));
        mode.addEffect(new GainLifeEffect(3).concatBy("and"));
        this.getSpellAbility().addMode(mode);

        // • Maestros Charm deals 5 damage to target creature or planeswalker.
        mode = new Mode(new DamageTargetEffect(5));
        mode.addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addMode(mode);
    }

    private MaestrosCharm(final MaestrosCharm card) {
        super(card);
    }

    @Override
    public MaestrosCharm copy() {
        return new MaestrosCharm(this);
    }
}

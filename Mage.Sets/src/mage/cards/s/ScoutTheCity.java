package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScoutTheCity extends CardImpl {

    public ScoutTheCity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Choose one --
        // * Look Around -- Mill three cards. You may put a permanent card from among them into your hand. You gain 3 life.
        this.getSpellAbility().addEffect(new MillThenPutInHandEffect(3, StaticFilters.FILTER_CARD_PERMANENT));
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
        this.getSpellAbility().withFirstModeFlavorWord("Look Around");

        // * Bring Down -- Destroy target creature with flying.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect())
                .addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING))
                .withFlavorWord("Bring Down"));
    }

    private ScoutTheCity(final ScoutTheCity card) {
        super(card);
    }

    @Override
    public ScoutTheCity copy() {
        return new ScoutTheCity(this);
    }
}

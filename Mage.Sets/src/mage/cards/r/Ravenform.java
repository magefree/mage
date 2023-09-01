package mage.cards.r;

import mage.abilities.effects.common.CreateTokenControllerTargetPermanentEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BlueBirdToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Ravenform extends CardImpl {

    public Ravenform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Exile target artifact or creature. Its controller creates a 1/1 blue Bird creature token with flying.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenControllerTargetPermanentEffect(new BlueBirdToken()));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));

        // Foretell {U}
        this.addAbility(new ForetellAbility(this, "{U}"));
    }

    private Ravenform(final Ravenform card) {
        super(card);
    }

    @Override
    public Ravenform copy() {
        return new Ravenform(this);
    }
}
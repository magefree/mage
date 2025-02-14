package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.common.TargetOpponent;

import java.util.UUID;


/**
 *
 * @author jam736
 */
public final class AncientVendetta extends CardImpl {

    public AncientVendetta(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Choose a card name. Search target opponent’s graveyard, hand, and library for up to four cards with that name and exile them. Then that player shuffles.
        this.getSpellAbility().addEffect((new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL)));
        this.getSpellAbility().addEffect(new AncientVendettaEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private AncientVendetta(final AncientVendetta card) {
        super(card);
    }

    @Override
    public AncientVendetta copy() {
        return new AncientVendetta(this);
    }
}

class AncientVendettaEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    AncientVendettaEffect() {
        super(true, "target opponent's", "up to four cards with that name", false, 4);
    }

    private AncientVendettaEffect(final AncientVendettaEffect effect) {
        super(effect);
    }

    @Override
    public AncientVendettaEffect copy() {
        return new AncientVendettaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String chosenCardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        return applySearchAndExile(game, source, chosenCardName, getTargetPointer().getFirst(game, source));
    }
}

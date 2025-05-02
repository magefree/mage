package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheStoneBrain extends CardImpl {

    public TheStoneBrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);

        // {2}, {T}, Exile The Stone Brain: Choose a card name. Search target opponent's graveyard, hand, and library for up to four cards with that name and exile them. That player shuffles, then draws a card for each card exiled from their hand this way. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        ability.addEffect(new TheStoneBrainEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private TheStoneBrain(final TheStoneBrain card) {
        super(card);
    }

    @Override
    public TheStoneBrain copy() {
        return new TheStoneBrain(this);
    }
}

class TheStoneBrainEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    TheStoneBrainEffect() {
        super(true, "target opponent's", "up to four cards with that name", true, 4);
    }

    private TheStoneBrainEffect(final TheStoneBrainEffect effect) {
        super(effect);
    }

    @Override
    public TheStoneBrainEffect copy() {
        return new TheStoneBrainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        return applySearchAndExile(game, source, cardName, getTargetPointer().getFirst(game, source));
    }
}

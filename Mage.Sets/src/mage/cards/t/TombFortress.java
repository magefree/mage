package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TombFortress extends CardImpl {

    public TombFortress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Tomb Fortress enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {2}{B}{B}{B}, {T}, Exile Tomb Fortress: Mill four cards, then return a creature card from your graveyard to the battlefield. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new MillCardsControllerEffect(4), new ManaCostsImpl<>("{2}{B}{B}{B}")
        );
        ability.addEffect(new ReturnCardChosenFromGraveyardEffect(false,
                StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD, PutCards.BATTLEFIELD).concatBy(", then"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private TombFortress(final TombFortress card) {
        super(card);
    }

    @Override
    public TombFortress copy() {
        return new TombFortress(this);
    }
}

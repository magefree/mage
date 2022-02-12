package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BloodToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodFountain extends CardImpl {

    public BloodFountain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{B}");

        // When Blood Fountain enters the battlefield, create a Blood token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BloodToken())));

        // {3}{B}, {T}, Sacrifice Blood Fountain: Return up to two target creature cards from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(
                new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl<>("{3}{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(
                0, 2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD
        ));
        this.addAbility(ability);
    }

    private BloodFountain(final BloodFountain card) {
        super(card);
    }

    @Override
    public BloodFountain copy() {
        return new BloodFountain(this);
    }
}

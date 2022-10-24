package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SkeletonMenaceToken;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AltarOfBhaal extends AdventureCard {

    public AltarOfBhaal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, new CardType[]{CardType.SORCERY}, "{1}{B}", "Bone Offering", "{2}{B}");

        // {2}{B}, {T}, Exile a creature you control: Return target creature card from your graveyard to the battlefield. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl<>("{2}{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_A_CREATURE)));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // Bone Offering
        // Create a tapped 4/1 black Skeleton creature token with menace.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new SkeletonMenaceToken(), 1, true, false));
    }

    private AltarOfBhaal(final AltarOfBhaal card) {
        super(card);
    }

    @Override
    public AltarOfBhaal copy() {
        return new AltarOfBhaal(this);
    }
}

package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.abilities.effects.common.continuous.GainControlAllControlledTargetEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class TezzeretMasterOfMetal extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts and creatures");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate()));
    }

    public TezzeretMasterOfMetal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEZZERET);

        this.setStartingLoyalty(5);

        // +1: Reveal cards from the top of your library until you reveal an artifact card. Put that card into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new LoyaltyAbility(new RevealCardsFromLibraryUntilEffect(StaticFilters.FILTER_CARD_ARTIFACT, PutCards.HAND, PutCards.BOTTOM_RANDOM), 1));

        // -3: Target opponent loses life equal to the number of artifacts you control.
        Ability ability = new LoyaltyAbility(new LoseLifeTargetEffect(ArtifactYouControlCount.instance).setText("target opponent loses life equal to the number of artifacts you control"), -3);
        ability.addTarget(new TargetOpponent());
        ability.addHint(ArtifactYouControlHint.instance);
        this.addAbility(ability);

        // -8: Gain control of all artifacts and creatures target opponent controls.
        ability = new LoyaltyAbility(new GainControlAllControlledTargetEffect(filter), -8);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private TezzeretMasterOfMetal(final TezzeretMasterOfMetal card) {
        super(card);
    }

    @Override
    public TezzeretMasterOfMetal copy() {
        return new TezzeretMasterOfMetal(this);
    }
}
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Scaretiller extends CardImpl {

    private static final FilterLandCard filter = new FilterLandCard("land card from your graveyard");

    public Scaretiller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever Scaretiller becomes tapped, choose one —
        // • You may put a land card from your hand onto the battlefield tapped.
        Ability ability = new BecomesTappedSourceTriggeredAbility(new PutCardFromHandOntoBattlefieldEffect(
                StaticFilters.FILTER_CARD_LAND_A, false, true
        ));

        // • Return target land card from your graveyard to the battlefield tapped.
        Mode mode = new Mode(new ReturnFromGraveyardToBattlefieldTargetEffect(true));
        mode.addTarget(new TargetCardInYourGraveyard(filter));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private Scaretiller(final Scaretiller card) {
        super(card);
    }

    @Override
    public Scaretiller copy() {
        return new Scaretiller(this);
    }
}

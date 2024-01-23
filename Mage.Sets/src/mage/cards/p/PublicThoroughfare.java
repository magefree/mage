package mage.cards.p;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PublicThoroughfare extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("untapped artifact or land you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public PublicThoroughfare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Public Thoroughfare enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Public Thoroughfare enters the battlefield, sacrifice it unless you tap an untapped artifact or land you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(

                new SacrificeSourceUnlessPaysEffect(new TapTargetCost(new TargetControlledPermanent(filter)))
        ));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private PublicThoroughfare(final PublicThoroughfare card) {
        super(card);
    }

    @Override
    public PublicThoroughfare copy() {
        return new PublicThoroughfare(this);
    }
}

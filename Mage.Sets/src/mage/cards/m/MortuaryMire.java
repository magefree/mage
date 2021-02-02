
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class MortuaryMire extends CardImpl {

    public MortuaryMire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Mortuary Mire enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Mortuary Mire enters the battlefield, you may put target creature card from your graveyard on top of your library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PutOnLibraryTargetEffect(true).setText("put target creature card from your graveyard on top of your library"), true);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());
    }

    private MortuaryMire(final MortuaryMire card) {
        super(card);
    }

    @Override
    public MortuaryMire copy() {
        return new MortuaryMire(this);
    }
}

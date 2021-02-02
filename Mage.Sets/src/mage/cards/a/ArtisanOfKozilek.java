
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Loki
 */
public final class ArtisanOfKozilek extends CardImpl {

    public ArtisanOfKozilek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{9}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(10);
        this.toughness = new MageInt(9);
        // When you cast Artisan of Kozilek, you may return target creature card from your graveyard to the battlefield.
        Ability ability = new CastSourceTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
        // Annihilator 2
        this.addAbility(new AnnihilatorAbility(2));
    }

    private ArtisanOfKozilek(final ArtisanOfKozilek card) {
        super(card);
    }

    @Override
    public ArtisanOfKozilek copy() {
        return new ArtisanOfKozilek(this);
    }

}

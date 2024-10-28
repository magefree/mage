package mage.cards.a;

import mage.MageInt;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AbhorrentOculus extends CardImpl {

    public AbhorrentOculus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.EYE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // As an additional cost to cast this spell, exile six cards from your graveyard.
        this.getSpellAbility().addCost(new ExileFromGraveCost(
                new TargetCardInYourGraveyard(6, StaticFilters.FILTER_CARDS_FROM_YOUR_GRAVEYARD)
        ));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each opponent's upkeep, manifest dread.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.OPPONENT, new ManifestDreadEffect(), false
        ));
    }

    private AbhorrentOculus(final AbhorrentOculus card) {
        super(card);
    }

    @Override
    public AbhorrentOculus copy() {
        return new AbhorrentOculus(this);
    }
}

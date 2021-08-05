package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SteelfinWhale extends CardImpl {

    public SteelfinWhale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.WHALE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // Whenever an artifact enters the battlefield under your control, untap Steelfin Whale.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new UntapSourceEffect(), StaticFilters.FILTER_PERMANENT_ARTIFACT_AN
        ));
    }

    private SteelfinWhale(final SteelfinWhale card) {
        super(card);
    }

    @Override
    public SteelfinWhale copy() {
        return new SteelfinWhale(this);
    }
}

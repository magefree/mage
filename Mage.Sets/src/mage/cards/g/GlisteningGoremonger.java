package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlisteningGoremonger extends CardImpl {

    public GlisteningGoremonger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Glistening Goremonger dies, each opponent sacrifices an artifact or creature.
        this.addAbility(new DiesSourceTriggeredAbility(new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE)));
    }

    private GlisteningGoremonger(final GlisteningGoremonger card) {
        super(card);
    }

    @Override
    public GlisteningGoremonger copy() {
        return new GlisteningGoremonger(this);
    }
}

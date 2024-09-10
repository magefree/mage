package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ReturnFromGraveyardAtRandomEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class MoldgrafMonstrosity extends CardImpl {

    public MoldgrafMonstrosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        this.addAbility(TrampleAbility.getInstance());

        // When Moldgraf Monstrosity dies, exile it, then return two creature cards at random from your graveyard to the battlefield.
        Ability ability = new DiesSourceTriggeredAbility(new ExileSourceEffect().setText("exile it"));
        ability.addEffect(new ReturnFromGraveyardAtRandomEffect(StaticFilters.FILTER_CARD_CREATURES, Zone.BATTLEFIELD, 2)
                .concatBy(", then"));
        this.addAbility(ability);
    }

    private MoldgrafMonstrosity(final MoldgrafMonstrosity card) {
        super(card);
    }

    @Override
    public MoldgrafMonstrosity copy() {
        return new MoldgrafMonstrosity(this);
    }
}

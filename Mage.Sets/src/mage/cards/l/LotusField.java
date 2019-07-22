package mage.cards.l;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LotusField extends CardImpl {

    public LotusField(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // Lotus Field enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Lotus Field enters the battlefield, sacrifice two lands.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeControllerEffect(StaticFilters.FILTER_LANDS, 2, "")
        ));

        // {T}: Add three mana of any color.
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(3), new TapSourceCost()
        ));
    }

    private LotusField(final LotusField card) {
        super(card);
    }

    @Override
    public LotusField copy() {
        return new LotusField(this);
    }
}

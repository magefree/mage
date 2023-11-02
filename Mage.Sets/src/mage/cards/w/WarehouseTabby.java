package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.RatCantBlockToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarehouseTabby extends CardImpl {

    public WarehouseTabby(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever an enchantment you control is put into a graveyard from the battlefield, create a 1/1 black Rat creature token with "This creature can't block."
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new CreateTokenEffect(new RatCantBlockToken()), false,
                StaticFilters.FILTER_CONTROLLED_PERMANENT_AN_ENCHANTMENT, false
        ));

        // {1}{B}: Warehouse Tabby gains deathtouch until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{1}{B}")));
    }

    private WarehouseTabby(final WarehouseTabby card) {
        super(card);
    }

    @Override
    public WarehouseTabby copy() {
        return new WarehouseTabby(this);
    }
}

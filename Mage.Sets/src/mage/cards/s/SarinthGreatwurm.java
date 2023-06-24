package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.PowerstoneToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SarinthGreatwurm extends CardImpl {

    public SarinthGreatwurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{G}");

        this.subtype.add(SubType.WURM);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a land enters the battlefield, create a tapped Powerstone token.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new CreateTokenEffect(new PowerstoneToken(), 1, true), StaticFilters.FILTER_LAND_A
        ));
    }

    private SarinthGreatwurm(final SarinthGreatwurm card) {
        super(card);
    }

    @Override
    public SarinthGreatwurm copy() {
        return new SarinthGreatwurm(this);
    }
}

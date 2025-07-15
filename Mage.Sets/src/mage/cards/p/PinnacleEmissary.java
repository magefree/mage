package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.DroneToken2;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PinnacleEmissary extends CardImpl {

    public PinnacleEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}{R}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast an artifact spell, create a 1/1 colorless Drone artifact creature token with flying and "This token can block only creatures with flying."
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new DroneToken2()), StaticFilters.FILTER_SPELL_AN_ARTIFACT, false
        ));

        // Warp {U/R}
        this.addAbility(new WarpAbility(this, "{U/R}"));
    }

    private PinnacleEmissary(final PinnacleEmissary card) {
        super(card);
    }

    @Override
    public PinnacleEmissary copy() {
        return new PinnacleEmissary(this);
    }
}

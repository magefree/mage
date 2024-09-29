package mage.cards.g;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.GoblinToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoblinWarParty extends CardImpl {

    public GoblinWarParty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Choose one —
        // • Create three 1/1 red Goblin creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new GoblinToken(), 3));

        // • Creatures you control get +1/+1 and gain haste until end of turn.
        Mode mode = new Mode(new BoostControlledEffect(
                1, 1, Duration.EndOfTurn
        ).setText("creatures you control get +1/+1"));
        mode.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("and gain haste until end of turn"));
        this.getSpellAbility().addMode(mode);

        // Entwine {2}{R}
        this.addAbility(new EntwineAbility("{2}{R}"));
    }

    private GoblinWarParty(final GoblinWarParty card) {
        super(card);
    }

    @Override
    public GoblinWarParty copy() {
        return new GoblinWarParty(this);
    }
}

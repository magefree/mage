package mage.cards.r;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.HumanSoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RallyAtTheHornburg extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.HUMAN, "Humans");

    public RallyAtTheHornburg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Create two 1/1 white Human Soldier creature tokens. Humans you control gain haste until end of turn.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new HumanSoldierToken(), 2));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn, filter
        ));
    }

    private RallyAtTheHornburg(final RallyAtTheHornburg card) {
        super(card);
    }

    @Override
    public RallyAtTheHornburg copy() {
        return new RallyAtTheHornburg(this);
    }
}

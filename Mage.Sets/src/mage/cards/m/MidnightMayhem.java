package mage.cards.m;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.Gremlin11Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MidnightMayhem extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.GREMLIN, "");

    public MidnightMayhem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{W}");

        // Create three 1/1 red Gremlin creature tokens. Gremlins you control gain menace, lifelink, and haste until end of turn.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new Gremlin11Token(), 3));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                new MenaceAbility(true), Duration.EndOfTurn, filter
        ).setText("Gremlins you control gain menace"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn, filter
        ).setText(", lifelink"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn, filter
        ).setText(", and haste until end of turn"));
    }

    private MidnightMayhem(final MidnightMayhem card) {
        super(card);
    }

    @Override
    public MidnightMayhem copy() {
        return new MidnightMayhem(this);
    }
}

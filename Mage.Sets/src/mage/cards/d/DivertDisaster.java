package mage.cards.d;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.LanderToken;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DivertDisaster extends CardImpl {

    public DivertDisaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target spell unless its controller pays {2}. If they do, you create a Lander token.
        this.getSpellAbility().addEffect(
                new CounterUnlessPaysEffect(new GenericManaCost(2))
                        .withIfTheyDo(new CreateTokenEffect(new LanderToken()).setText("you create a Lander token"))
        );
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private DivertDisaster(final DivertDisaster card) {
        super(card);
    }

    @Override
    public DivertDisaster copy() {
        return new DivertDisaster(this);
    }
}
package mage.cards.f;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ZombieDecayedToken;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlipTheSwitch extends CardImpl {

    public FlipTheSwitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Counter target spell unless its controller pays {4}. Create a 2/2 black Zombie creature token with decayed.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(4)));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ZombieDecayedToken()));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private FlipTheSwitch(final FlipTheSwitch card) {
        super(card);
    }

    @Override
    public FlipTheSwitch copy() {
        return new FlipTheSwitch(this);
    }
}

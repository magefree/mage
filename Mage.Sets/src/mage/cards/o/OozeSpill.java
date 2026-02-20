package mage.cards.o;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.MutagenToken;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OozeSpill extends CardImpl {

    public OozeSpill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Counter target spell. Create a Mutagen token.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new MutagenToken()));
    }

    private OozeSpill(final OozeSpill card) {
        super(card);
    }

    @Override
    public OozeSpill copy() {
        return new OozeSpill(this);
    }
}

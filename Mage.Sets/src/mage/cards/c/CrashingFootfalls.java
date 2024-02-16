package mage.cards.c;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.RhinoToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrashingFootfalls extends CardImpl {

    public CrashingFootfalls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "");
        this.color.setGreen(true);

        // Suspend 4—{G}
        this.addAbility(new SuspendAbility(4, new ManaCostsImpl<>("{G}"), this));

        // Create two 4/4 green Rhino creature tokens with trample.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new RhinoToken(), 2));
    }

    private CrashingFootfalls(final CrashingFootfalls card) {
        super(card);
    }

    @Override
    public CrashingFootfalls copy() {
        return new CrashingFootfalls(this);
    }
}

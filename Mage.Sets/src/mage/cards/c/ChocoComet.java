package mage.cards.c;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ChocoboToken;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChocoComet extends CardImpl {

    public ChocoComet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");

        // Choco-Comet deals X damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(GetXValue.instance));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Create a 2/2 green Bird creature token with "Whenever a land you control enters, this token gets +1/+0 until end of turn."
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ChocoboToken()).concatBy("<br>"));
    }

    private ChocoComet(final ChocoComet card) {
        super(card);
    }

    @Override
    public ChocoComet copy() {
        return new ChocoComet(this);
    }
}

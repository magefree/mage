package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.CommanderStormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author TheElk801
 */
public final class EchoStorm extends CardImpl {

    public EchoStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // When you cast this spell, copy it for each time you've cast your commander from the command zone this game. You may choose new targets for the copies.
        this.addAbility(new CommanderStormAbility());

        // Create a token that's a copy of target artifact.
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
    }

    private EchoStorm(final EchoStorm card) {
        super(card);
    }

    @Override
    public EchoStorm copy() {
        return new EchoStorm(this);
    }
}

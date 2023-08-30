package mage.cards.r;

import mage.abilities.effects.common.CreateTokenControllerTargetPermanentEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Spirit32Token;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReduceToMemory extends CardImpl {

    public ReduceToMemory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{W}");

        this.subtype.add(SubType.LESSON);

        // Exile target nonland permanent. Its controller creates a 3/2 red and white spirit creature token.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenControllerTargetPermanentEffect(new Spirit32Token()));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private ReduceToMemory(final ReduceToMemory card) {
        super(card);
    }

    @Override
    public ReduceToMemory copy() {
        return new ReduceToMemory(this);
    }
}
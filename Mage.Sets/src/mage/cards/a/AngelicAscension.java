package mage.cards.a;

import mage.abilities.effects.common.CreateTokenControllerTargetPermanentEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.AngelToken;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AngelicAscension extends CardImpl {

    public AngelicAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Exile target creature or planeswalker. Its controller creates a 4/4 white Angel creature token with flying.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenControllerTargetPermanentEffect(new AngelToken()));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private AngelicAscension(final AngelicAscension card) {
        super(card);
    }

    @Override
    public AngelicAscension copy() {
        return new AngelicAscension(this);
    }
}
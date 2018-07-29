package mage.cards.c;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.OxTokenLorado;

import java.util.UUID;

/**
 *
 * @author EikePeace
 */

public final class CattleDrive extends CardImpl {

    public CattleDrive(UUID ownerId, CardSetInfo setInfo){
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY},"{3}{W}");


        //Create three 1/1 white Ox creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new OxTokenLorado(), 3));
    }

    public CattleDrive(final CattleDrive card) {
        super(card);
    }

    @Override
    public CattleDrive copy() {
        return new CattleDrive(this);
    }
}

package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.PhyrexianMiteToken;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChargeOfTheMites extends CardImpl {

    public ChargeOfTheMites(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Choose one--
        // * Charge of the Mites deals damage equal to the number of creatures you control to target creature or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(CreaturesYouControlCount.instance)
                .setText("{this} deals damage equal to the number of creatures you control to target creature or planeswalker"));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addHint(CreaturesYouControlHint.instance);

        // * Create two 1/1 colorless Phyrexian Mite artifact creature tokens with toxic 1 and "This creature can't block."
        this.getSpellAbility().addMode(new Mode(new CreateTokenEffect(new PhyrexianMiteToken(), 2)));
    }

    private ChargeOfTheMites(final ChargeOfTheMites card) {
        super(card);
    }

    @Override
    public ChargeOfTheMites copy() {
        return new ChargeOfTheMites(this);
    }
}

package mage.cards.r;

import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiseOfTheEldrazi extends CardImpl {

    public RiseOfTheEldrazi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{9}{C}{C}{C}");

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Destroy target permanent. Target player draws four cards. Take an extra turn after this one.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(4).setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new AddExtraTurnControllerEffect());

        // Exile Rise of the Eldrazi.
        this.getSpellAbility().addEffect(new ExileSpellEffect().concatBy("<br>"));
    }

    private RiseOfTheEldrazi(final RiseOfTheEldrazi card) {
        super(card);
    }

    @Override
    public RiseOfTheEldrazi copy() {
        return new RiseOfTheEldrazi(this);
    }
}

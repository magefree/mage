package mage.cards.e;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.game.permanent.token.EldraziScionToken;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author grimreap124
 */
public final class EldraziConfluence extends CardImpl {

    public EldraziConfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{C}{C}");
        

        // Choose three. You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMinModes(3);
        this.getSpellAbility().getModes().setMaxModes(3);
        this.getSpellAbility().getModes().setMayChooseSameModeMoreThanOnce(true);
        // * Target creature gets +3/-3 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, -3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // * Exile target nonland permanent, then return it to the battlefield tapped under its owner's control.
        this.getSpellAbility().addMode(new Mode(new ExileThenReturnTargetEffect(false, false, PutCards.BATTLEFIELD_TAPPED)).addTarget(new TargetNonlandPermanent()));
        // * Create a 1/1 colorless Eldrazi Scion creature token with "Sacrifice this creature: Add {C}."
        this.getSpellAbility().addMode(new Mode(new CreateTokenEffect(new EldraziScionToken())));
    }

    private EldraziConfluence(final EldraziConfluence card) {
        super(card);
    }

    @Override
    public EldraziConfluence copy() {
        return new EldraziConfluence(this);
    }
}

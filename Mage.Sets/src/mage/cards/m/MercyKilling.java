package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GreenWhiteElfWarriorToken;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author duncant
 */
public final class MercyKilling extends CardImpl {

    public MercyKilling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G/W}");

        // Target creature's controller sacrifices it, then creates X 1/1 green and white Elf Warrior creature tokens, where X is that creature's power.
        this.getSpellAbility().addEffect(new SacrificeTargetEffect("Target creature's controller sacrifices it"));
        this.getSpellAbility().addEffect(new MercyKillingTokenEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private MercyKilling(final MercyKilling card) {
        super(card);
    }

    @Override
    public MercyKilling copy() {
        return new MercyKilling(this);
    }
}

class MercyKillingTokenEffect extends OneShotEffect {

    public MercyKillingTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = ", then creates X 1/1 green and white Elf Warrior creature tokens, where X is that creature's power";
    }

    public MercyKillingTokenEffect(final MercyKillingTokenEffect effect) {
        super(effect);
    }

    @Override
    public MercyKillingTokenEffect copy() {
        return new MercyKillingTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            int power = permanent.getPower().getValue();
            return new GreenWhiteElfWarriorToken().putOntoBattlefield(power, game, source, permanent.getControllerId());
        }
        return false;
    }

}

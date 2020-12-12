package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AngelToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 * @author TheElk801
 */
public final class AngelicAscension extends CardImpl {

    public AngelicAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Exile target creature or planeswalker. Its controller creates a 4/4 white Angel creature token with flying.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new AngelicAscensionEffect());
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

class AngelicAscensionEffect extends OneShotEffect {

    private static final Token token = new AngelToken();

    AngelicAscensionEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Its controller creates a 4/4 white Angel creature token with flying";
    }

    private AngelicAscensionEffect(final AngelicAscensionEffect effect) {
        super(effect);
    }

    @Override
    public AngelicAscensionEffect copy() {
        return new AngelicAscensionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent == null) {
            return false;
        }
        return token.putOntoBattlefield(1, game, source, permanent.getControllerId());
    }
}

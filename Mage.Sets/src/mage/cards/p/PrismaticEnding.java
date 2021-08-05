package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrismaticEnding extends CardImpl {

    public PrismaticEnding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{W}");

        // Converge — Exile target nonland permanent if its mana value is less than or equal to the number of colors of mana spent to cast this spell.
        this.getSpellAbility().addEffect(new PrismaticEndingEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private PrismaticEnding(final PrismaticEnding card) {
        super(card);
    }

    @Override
    public PrismaticEnding copy() {
        return new PrismaticEnding(this);
    }
}

class PrismaticEndingEffect extends OneShotEffect {

    PrismaticEndingEffect() {
        super(Outcome.Benefit);
        staticText = "<i>Converge</i> &mdash; Exile target nonland permanent if its mana value " +
                "is less than or equal to the number of colors of mana spent to cast this spell";
    }

    private PrismaticEndingEffect(final PrismaticEndingEffect effect) {
        super(effect);
    }

    @Override
    public PrismaticEndingEffect copy() {
        return new PrismaticEndingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        int colors = ColorsOfManaSpentToCastCount.getInstance().calculate(game, source, this);
        return player != null
                && permanent != null
                && permanent.getManaValue() <= colors
                && player.moveCards(permanent, Zone.EXILED, source, game);
    }
}

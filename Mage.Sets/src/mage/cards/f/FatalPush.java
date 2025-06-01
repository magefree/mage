package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class FatalPush extends CardImpl {

    public FatalPush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Destroy target creature if it has converted mana cost 2 or less.
        // <i>Revolt</i> &mdash; Destroy that creature if it has converted mana cost 4 or less instead if a permanent you controlled left the battlefield this turn.
        this.getSpellAbility().addEffect(new FatalPushEffect());
        this.getSpellAbility().addWatcher(new RevoltWatcher());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(RevoltCondition.getHint());
    }

    private FatalPush(final FatalPush card) {
        super(card);
    }

    @Override
    public FatalPush copy() {
        return new FatalPush(this);
    }
}

class FatalPushEffect extends OneShotEffect {

    FatalPushEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature if it has mana value 2 or less.<br>" +
                AbilityWord.REVOLT.formatWord() + "Destroy that creature if it has mana value 4 " +
                "or less instead if a permanent left the battlefield under your control this turn";
    }

    private FatalPushEffect(final FatalPushEffect effect) {
        super(effect);
    }

    @Override
    public FatalPushEffect copy() {
        return new FatalPushEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (targetCreature == null) {
            return false;
        }
        int cmc = targetCreature.getManaValue();
        if ((cmc <= 2 || (RevoltCondition.instance.apply(game, source) && cmc <= 4))
                && targetCreature.destroy(source, game, false)) {
            return true;
        }
        return false;
    }
}

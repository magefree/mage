package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchaicsAgony extends CardImpl {

    public ArchaicsAgony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Converge -- Archaic's Agony deals X damage to target creature, where X is the number of colors of mana spent to cast this spell. Exile cards from the top of your library equal to the excess damage dealt to that creature this way. You may play those cards until the end of your next turn.
        this.getSpellAbility().addEffect(new ArchaicsAgonyEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().setAbilityWord(AbilityWord.CONVERGE);
    }

    private ArchaicsAgony(final ArchaicsAgony card) {
        super(card);
    }

    @Override
    public ArchaicsAgony copy() {
        return new ArchaicsAgony(this);
    }
}

class ArchaicsAgonyEffect extends OneShotEffect {

    ArchaicsAgonyEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals X damage to target creature, where X is the number of colors of mana " +
                "spent to cast this spell. Exile cards from the top of your library equal to the excess damage " +
                "dealt to that creature this way. You may play those cards until the end of your next turn";
    }

    private ArchaicsAgonyEffect(final ArchaicsAgonyEffect effect) {
        super(effect);
    }

    @Override
    public ArchaicsAgonyEffect copy() {
        return new ArchaicsAgonyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        int amount = ColorsOfManaSpentToCastCount.getInstance().calculate(game, source, this);
        if (permanent == null || amount < 1) {
            return false;
        }
        int excess = permanent.damageWithExcess(amount, source, game);
        if (excess > 0) {
            new ExileTopXMayPlayUntilEffect(excess, Duration.UntilEndOfYourNextTurn).apply(game, source);
        }
        return true;
    }
}

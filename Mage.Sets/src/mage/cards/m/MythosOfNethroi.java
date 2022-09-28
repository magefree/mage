package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MythosOfNethroi extends CardImpl {

    public MythosOfNethroi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Destroy target nonland permanent if it's a creature or if {G}{W} was spent to cast this spell.
        this.getSpellAbility().addEffect(new MythosOfNethroiEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private MythosOfNethroi(final MythosOfNethroi card) {
        super(card);
    }

    @Override
    public MythosOfNethroi copy() {
        return new MythosOfNethroi(this);
    }
}

class MythosOfNethroiEffect extends OneShotEffect {

    private static final Condition condition = new CompoundCondition(
            new ManaWasSpentCondition(ColoredManaSymbol.G),
            new ManaWasSpentCondition(ColoredManaSymbol.W)
    );

    MythosOfNethroiEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy target nonland permanent if it's a creature or if {G}{W} was spent to cast this spell.";
    }

    private MythosOfNethroiEffect(final MythosOfNethroiEffect effect) {
        super(effect);
    }

    @Override
    public MythosOfNethroiEffect copy() {
        return new MythosOfNethroiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null || (!permanent.isCreature(game) && !condition.apply(game, source))) {
            return false;
        }
        return permanent.destroy(source, game, false);
    }

    @Override
    public Condition getCondition() {
        return condition;
    }
}
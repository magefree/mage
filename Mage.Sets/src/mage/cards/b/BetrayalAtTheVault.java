package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class BetrayalAtTheVault extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other target creatures");

    static {
        filter.add(new AnotherTargetPredicate(2));
    }

    public BetrayalAtTheVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{G}{G}");

        // Target creature you control deals damage equal to its power to each of two other target creatures.
        this.getSpellAbility().addEffect(new BetrayalAtTheVaultEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent().setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2, 2, filter, false).setTargetTag(2));
    }

    private BetrayalAtTheVault(final BetrayalAtTheVault card) {
        super(card);
    }

    @Override
    public BetrayalAtTheVault copy() {
        return new BetrayalAtTheVault(this);
    }
}

class BetrayalAtTheVaultEffect extends OneShotEffect {

    BetrayalAtTheVaultEffect() {
        super(Outcome.Benefit);
        staticText = "Target creature you control deals damage equal to its power "
                + "to each of two other target creatures";
    }

    private BetrayalAtTheVaultEffect(final BetrayalAtTheVaultEffect effect) {
        super(effect);
    }

    @Override
    public BetrayalAtTheVaultEffect copy() {
        return new BetrayalAtTheVaultEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature == null) {
            return false;
        }
        source.getTargets()
                .stream()
                .filter(t -> t.getTargetTag() == 2)
                .map(Target::getTargets)
                .flatMap(List::stream)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .forEach(p -> p.damage(creature.getPower().getValue(), creature.getId(), source, game));
        return true;
    }
}
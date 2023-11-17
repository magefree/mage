package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureAllEffect;
import mage.abilities.effects.common.continuous.BecomesSubtypeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCardHalf;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class CyberConversion extends CardImpl {

    public CyberConversion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}");

        // Turn target creature face down. It's a 2/2 Cyberman artifact creature.
        this.getSpellAbility().addEffect(new CyberConversionEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private CyberConversion(final CyberConversion card) {
        super(card);
    }

    @Override
    public CyberConversion copy() {
        return new CyberConversion(this);
    }
}

class CyberConversionEffect extends OneShotEffect {

    CyberConversionEffect() {
        super(Outcome.UnboostCreature);
        this.staticText = "Turn target creature face down. It's a 2/2 Cyberman artifact creature";
    }

    private CyberConversionEffect(final CyberConversionEffect effect) {
        super(effect);
    }

    @Override
    public CyberConversionEffect copy() {
        return new CyberConversionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Predicate<Permanent> pred = new PermanentIdPredicate(UUID.randomUUID());
        for (Target target : source.getTargets()) {
            for (UUID targetId : target.getTargets()) {
                if (!game.getPermanent(targetId).isTransformable() && !(game.getCard(targetId) instanceof ModalDoubleFacedCardHalf)) {
                    pred = Predicates.or(pred, new PermanentIdPredicate(targetId));
                }
            }
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(pred);

        game.addEffect(new BecomesFaceDownCreatureAllEffect(filter), source);
        game.addEffect(new BecomesSubtypeAllEffect(Duration.WhileOnBattlefield, Arrays.asList(SubType.CYBERMAN), filter, false), source);
        for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            ContinuousEffect effect = new AddCardTypeTargetEffect(Duration.WhileOnBattlefield, CardType.ARTIFACT);
            effect.setTargetPointer(new FixedTarget(perm, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}

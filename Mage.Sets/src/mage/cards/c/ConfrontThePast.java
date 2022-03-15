package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterPermanentCard;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetPlaneswalkerPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class ConfrontThePast extends CardImpl {

    public static final FilterPlaneswalkerPermanent filter = new FilterPlaneswalkerPermanent();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public ConfrontThePast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}");
        
        this.subtype.add(SubType.LESSON);

        // Choose one —
        // • Return target planeswalker card with mana value X or less from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect()
            .setText("return target planeswalker card with mana value X or less from your graveyard to the battlefield"));
        this.getSpellAbility().setTargetAdjuster(ConfrontThePastAdjuster.instance);

        // • Remove twice X loyalty counters from target planeswalker an opponent controls.
        Mode mode = new Mode(new ConfrontThePastLoyaltyEffect());
        mode.addTarget(new TargetPlaneswalkerPermanent(filter));
        this.getSpellAbility().addMode(mode);
    }

    private ConfrontThePast(final ConfrontThePast card) {
        super(card);
    }

    @Override
    public ConfrontThePast copy() {
        return new ConfrontThePast(this);
    }
}

enum ConfrontThePastAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getEffects().size() == 1
                && ability.getEffects().get(0) instanceof  ReturnFromGraveyardToBattlefieldTargetEffect) {
            int xValue = ability.getManaCostsToPay().getX();
            ability.getTargets().clear();
            FilterPermanentCard filter = new FilterPermanentCard("planeswalker card with mana value X or less");
            filter.add(CardType.PLANESWALKER.getPredicate());
            filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
            ability.addTarget(new TargetCardInYourGraveyard(filter));
        }
    }
}

class ConfrontThePastLoyaltyEffect extends OneShotEffect {

    ConfrontThePastLoyaltyEffect() {
        super(Outcome.Benefit);
        staticText = "remove twice X loyalty counters from target planeswalker an opponent controls";
    }

    public ConfrontThePastLoyaltyEffect(ConfrontThePastLoyaltyEffect effect) {
        super(effect);
    }

    @Override
    public ConfrontThePastLoyaltyEffect copy() {
        return new ConfrontThePastLoyaltyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = source.getManaCostsToPay().getX();
        Permanent target = game.getPermanent(source.getFirstTarget());
        target.removeCounters(CounterType.LOYALTY.createInstance(xValue * 2), source, game);
        return true;
    }
}
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author htrajan
 */
public final class ConfrontThePast extends CardImpl {

    public static final FilterPermanent filter
            = new FilterPlaneswalkerPermanent("planeswalker an opponent controls");

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
        Mode mode = new Mode(new RemoveCounterTargetEffect(
                CounterType.LOYALTY.createInstance(), new MultipliedValue(GetXValue.instance, 2)));
        mode.addTarget(new TargetPermanent(filter));
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
                && ability.getEffects().get(0) instanceof ReturnFromGraveyardToBattlefieldTargetEffect) {
            int xValue = CardUtil.getSourceCostsTag(game, ability, "X", 0);
            ability.getTargets().clear();
            FilterPermanentCard filter = new FilterPermanentCard("planeswalker card with mana value X or less");
            filter.add(CardType.PLANESWALKER.getPredicate());
            filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
            ability.addTarget(new TargetCardInYourGraveyard(filter));
        }
    }
}

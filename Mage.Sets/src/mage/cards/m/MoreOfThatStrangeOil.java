package mage.cards.m;

import mage.MageObject;
import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author tiera3 - based on GetOut
 */
public final class MoreOfThatStrangeOil extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("creature, artifact, or planeswalker spell");
    private static final Predicate<MageObject> predicate = Predicates.or(
            CardType.CREATURE.getPredicate(),
            CardType.ARTIFACT.getPredicate(),
            CardType.PLANESWALKER.getPredicate()
    );

    static {
        filter.add(predicate);
    }

    public MoreOfThatStrangeOil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Choose one --
        // • Proliferate. Draw a card.
        this.getSpellAbility().addEffect(new ProliferateEffect());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy(". "));

        // * Counter target creature or enchantment spell.
        mode = new Mode(new CounterTargetEffect());
        mode.addTarget(new TargetSpell(filter));
        this.getSpellAbility().addMode(mode);
    }

    private MoreOfThatStrangeOil(final MoreOfThatStrangeOil card) {
        super(card);
    }

    @Override
    public MoreOfThatStrangeOil copy() {
        return new MoreOfThatStrangeOil(this);
    }
}

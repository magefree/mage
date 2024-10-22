package mage.cards.m;

import mage.MageObject;
import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
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
    static {
        filter.add(Predicates.or(
            CardType.CREATURE.getPredicate(),
            CardType.ARTIFACT.getPredicate(),
            CardType.PLANESWALKER.getPredicate()
        ));
    }

    public MoreOfThatStrangeOil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Choose one --
        // • Proliferate. Draw a card.
        this.getSpellAbility().addEffect(new ProliferateEffect(false));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().withFirstModeFlavorWord("It’s Probably Nothing");

        // * Counter target creature, artifact, or planeswalker spell. Scry 1.
        Mode mode = new Mode(new CounterTargetEffect());
        mode.addTarget(new TargetSpell(filter));
        mode.addEffect(new ScryEffect(1, false));
        this.getSpellAbility().addMode(mode.withFlavorWord("That Could Actually Be Dangerous"));
    }

    private MoreOfThatStrangeOil(final MoreOfThatStrangeOil card) {
        super(card);
    }

    @Override
    public MoreOfThatStrangeOil copy() {
        return new MoreOfThatStrangeOil(this);
    }
}

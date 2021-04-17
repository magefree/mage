package mage.cards.q;

import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.TargetPlayerShufflesTargetCardsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.target.TargetPlayer;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInTargetPlayersGraveyard;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QuandrixCommand extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("artifact or enchantment spell");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public QuandrixCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}{U}");

        // Choose two —
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // • Return target creature or planeswalker to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        // • Counter target artifact or enchantment spell.
        Mode mode = new Mode(new CounterTargetEffect());
        mode.addTarget(new TargetSpell(filter));
        this.getSpellAbility().addMode(mode);

        // • Put two +1/+1 counters on target creature.
        mode = new Mode(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);

        // • Target player shuffles up to three target cards from their graveyard into their library.
        mode = new Mode(new TargetPlayerShufflesTargetCardsEffect());
        mode.addTarget(new TargetPlayer());
        mode.addTarget(new TargetCardInTargetPlayersGraveyard(3));
        this.getSpellAbility().addMode(mode);
    }

    private QuandrixCommand(final QuandrixCommand card) {
        super(card);
    }

    @Override
    public QuandrixCommand copy() {
        return new QuandrixCommand(this);
    }
}

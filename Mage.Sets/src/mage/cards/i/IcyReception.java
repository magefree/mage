package mage.cards.i;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class IcyReception extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("creature or legendary spell");

    static {
        filter.add(Predicates.or(
            CardType.CREATURE.getPredicate(),
            SuperType.LEGENDARY.getPredicate()
        ));
    }

    public IcyReception(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Choose one --
        // * Counter target creature or legendary spell unless its controller pays {3}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new ManaCostsImpl<>("{3}")));
        this.getSpellAbility().addTarget(new TargetSpell(filter));

        // * Target creature gets -5/-0 until end of turn.
        this.getSpellAbility().addMode(
            new Mode(new BoostTargetEffect(-5, 0)).addTarget(new TargetCreaturePermanent())
        );
    }

    private IcyReception(final IcyReception card) {
        super(card);
    }

    @Override
    public IcyReception copy() {
        return new IcyReception(this);
    }
}

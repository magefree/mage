package mage.cards.g;

import mage.MageObject;
import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
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
 * @author TheElk801
 */
public final class GetOut extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("creature or enchantment spell");
    private static final FilterPermanent filter2 = new FilterPermanent("creatures and/or enchantments you own to your hand");
    private static final Predicate<MageObject> predicate = Predicates.or(
            CardType.CREATURE.getPredicate(),
            CardType.ENCHANTMENT.getPredicate()
    );

    static {
        filter.add(predicate);
        filter2.add(predicate);
        filter2.add(TargetController.YOU.getOwnerPredicate());
    }

    public GetOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}");

        // Choose one --
        // * Counter target creature or enchantment spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));

        // * Return one or two target creatures and/or enchantments you own to your hand.
        this.getSpellAbility().addMode(new Mode(new ReturnToHandTargetEffect())
                .addTarget(new TargetPermanent(1, 2, filter2)));
    }

    private GetOut(final GetOut card) {
        super(card);
    }

    @Override
    public GetOut copy() {
        return new GetOut(this);
    }
}

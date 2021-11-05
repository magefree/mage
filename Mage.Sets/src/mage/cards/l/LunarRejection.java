package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.CleaveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LunarRejection extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("Wolf or Werewolf creature");

    static {
        filter.add(Predicates.or(
                SubType.WEREWOLF.getPredicate(),
                SubType.WOLF.getPredicate()
        ));
    }

    public LunarRejection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Cleave {3}{U}
        Ability ability = new CleaveAbility(this, new ReturnToHandTargetEffect(), "{3}{U}");
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Return target [Wolf or Werewolf] creature to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect()
                .setText("return target [Wolf or Werewolf] creature to its owner's hand"));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private LunarRejection(final LunarRejection card) {
        super(card);
    }

    @Override
    public LunarRejection copy() {
        return new LunarRejection(this);
    }
}

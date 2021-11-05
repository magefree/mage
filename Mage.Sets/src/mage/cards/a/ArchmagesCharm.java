package mage.cards.a;

import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchmagesCharm extends CardImpl {

    private static final FilterPermanent filter
            = new FilterNonlandPermanent("nonland permanent with mana value 1 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 2));
    }

    public ArchmagesCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}{U}");

        // Choose one —
        // • Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

        // • Target player draws two cards.
        Mode mode = new Mode(new DrawCardTargetEffect(2));
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);

        // • Gain control of target nonland permanent with converted mana cost 1 or less.
        mode = new Mode(new GainControlTargetEffect(Duration.EndOfGame, true));
        mode.addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addMode(mode);
    }

    private ArchmagesCharm(final ArchmagesCharm card) {
        super(card);
    }

    @Override
    public ArchmagesCharm copy() {
        return new ArchmagesCharm(this);
    }
}

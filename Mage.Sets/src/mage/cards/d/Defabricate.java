package mage.cards.d;

import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CounterTargetWithReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.target.TargetSpell;
import mage.target.common.TargetActivatedOrTriggeredAbility;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Defabricate extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("artifact or enchantment spell");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public Defabricate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Choose one --
        // * Counter target artifact or enchantment spell. If a spell is countered this way, exile it instead of putting it into its owner's graveyard.
        this.getSpellAbility().addEffect(new CounterTargetWithReplacementEffect(PutCards.EXILED));
        this.getSpellAbility().addTarget(new TargetSpell(filter));

        // * Counter target activated or triggered ability.
        this.getSpellAbility().addMode(new Mode(new CounterTargetEffect())
                .addTarget(new TargetActivatedOrTriggeredAbility()));
    }

    private Defabricate(final Defabricate card) {
        super(card);
    }

    @Override
    public Defabricate copy() {
        return new Defabricate(this);
    }
}

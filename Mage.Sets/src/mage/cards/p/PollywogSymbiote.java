package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.MutateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PollywogSymbiote extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard();
    private static final FilterSpell filter2 = new FilterCreatureSpell("a creature spell, if it has mutate");
    private static final AbilityPredicate predicate = new AbilityPredicate(MutateAbility.class);

    static {
        filter.add(predicate);
        filter2.add(predicate);
    }

    public PollywogSymbiote(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.FROG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Each creature spell you cast costs {1} less to cast if it has mutate.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)
                .setText("each creature spell you cast costs {1} less to cast if it has mutate")));

        // Whenever you cast a creature spell, if it has mutate, draw a card, then discard a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawDiscardControllerEffect(1, 1), filter2, false
        ));
    }

    private PollywogSymbiote(final PollywogSymbiote card) {
        super(card);
    }

    @Override
    public PollywogSymbiote copy() {
        return new PollywogSymbiote(this);
    }
}

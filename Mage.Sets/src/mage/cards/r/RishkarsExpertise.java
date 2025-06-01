package mage.cards.r;

import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class RishkarsExpertise extends CardImpl {

    private static final FilterCard filter = new FilterCard("a spell with mana value 5 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 6));
    }

    public RishkarsExpertise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // Draw cards equal to the greatest power among creatures you control.
        Effect effect = new DrawCardSourceControllerEffect(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES);
        effect.setText("Draw cards equal to the greatest power among creatures you control");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addHint(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.getHint());

        // You may cast a card with converted mana cost 5 or less from your hand without paying its mana cost.
        this.getSpellAbility().addEffect(new CastFromHandForFreeEffect(filter).concatBy("<br>"));
    }

    private RishkarsExpertise(final RishkarsExpertise card) {
        super(card);
    }

    @Override
    public RishkarsExpertise copy() {
        return new RishkarsExpertise(this);
    }
}

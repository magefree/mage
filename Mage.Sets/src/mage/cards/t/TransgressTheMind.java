package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileCardYouChooseTargetOpponentEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class TransgressTheMind extends CardImpl {

    private static final FilterCard filter = new FilterCard("a card from it with mana value 3 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 2));
    }

    public TransgressTheMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Target player reveals their hand. You may choose a card from it with converted mana cost 3 or greater and exile that card.
        Effect effect = new ExileCardYouChooseTargetOpponentEffect(filter);
        effect.setText("Target player reveals their hand. You choose a card from it with mana value 3 or greater and exile that card");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private TransgressTheMind(final TransgressTheMind card) {
        super(card);
    }

    @Override
    public TransgressTheMind copy() {
        return new TransgressTheMind(this);
    }
}

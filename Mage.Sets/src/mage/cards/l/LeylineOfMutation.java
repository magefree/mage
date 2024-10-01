package mage.cards.l;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.WUBRGInsteadEffect;
import mage.abilities.keyword.LeylineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeylineOfMutation extends CardImpl {

    public LeylineOfMutation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // If Leyline of Mutation is in your opening hand, you may begin the game with it on the battlefield.
        this.addAbility(LeylineAbility.getInstance());

        // You may pay {W}{U}{B}{R}{G} rather than pay the mana cost for spells that you cast.
        this.addAbility(new SimpleStaticAbility(new WUBRGInsteadEffect()));
    }

    private LeylineOfMutation(final LeylineOfMutation card) {
        super(card);
    }

    @Override
    public LeylineOfMutation copy() {
        return new LeylineOfMutation(this);
    }
}

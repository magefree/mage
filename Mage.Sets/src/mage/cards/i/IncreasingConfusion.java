package mage.cards.i;

import mage.abilities.condition.common.CastFromGraveyardSourceCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class IncreasingConfusion extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(GetXValue.instance, 2);

    public IncreasingConfusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}");

        // Target player puts the top X cards of their library into their graveyard. If this spell was cast from a graveyard, that player puts twice that many cards into their graveyard instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new MillCardsTargetEffect(xValue), new MillCardsTargetEffect(GetXValue.instance),
                CastFromGraveyardSourceCondition.instance, "target player mills X cards. " +
                "If this spell was cast from a graveyard, that player mills twice that many cards"
        ));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Flashback {X}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{X}{U}")));
    }

    private IncreasingConfusion(final IncreasingConfusion card) {
        super(card);
    }

    @Override
    public IncreasingConfusion copy() {
        return new IncreasingConfusion(this);
    }
}

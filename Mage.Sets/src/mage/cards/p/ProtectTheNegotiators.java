package mage.cards.p;

import java.util.UUID;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SoldierToken;
import mage.target.TargetSpell;

/**
 *
 * @author weirddan455
 */
public final class ProtectTheNegotiators extends CardImpl {

    public ProtectTheNegotiators(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Kicker {W}
        this.addAbility(new KickerAbility("{W}"));

        // If this spell was kicked, create a 1/1 white Soldier creature token.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new SoldierToken()),
                KickedCondition.ONCE,
                "If this was spell was kicked, create a 1/1 white Soldier creature token."
        ));

        // Counter target spell unless its controller pays {1} for each creature you control.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(CreaturesYouControlCount.instance)
                .setText("<br>Counter target spell unless its controller pays {1} for each creature you control."));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private ProtectTheNegotiators(final ProtectTheNegotiators card) {
        super(card);
    }

    @Override
    public ProtectTheNegotiators copy() {
        return new ProtectTheNegotiators(this);
    }
}

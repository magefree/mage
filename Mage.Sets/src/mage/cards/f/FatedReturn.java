package mage.cards.f;

import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FatedReturn extends CardImpl {

    public FatedReturn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}{B}{B}");


        // Put target creature card from a graveyard onto the battlefield under your control. It gains indestructible. If it's your turn, scry 2.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.Custom,
                "It gains indestructible"));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new ScryEffect(2), MyTurnCondition.instance,
                "If it's your turn, scry 2. <i>(Look at the top two cards of your library, then put any number of them on the bottom of your library and the rest on top in any order.)</i>"));
        this.getSpellAbility().addHint(MyTurnHint.instance);
    }

    private FatedReturn(final FatedReturn card) {
        super(card);
    }

    @Override
    public FatedReturn copy() {
        return new FatedReturn(this);
    }
}

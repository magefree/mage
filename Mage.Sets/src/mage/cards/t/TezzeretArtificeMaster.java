package mage.cards.t;

import mage.abilities.LoyaltyAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.hint.common.MetalcraftHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.command.emblems.TezzeretArtificeMasterEmblem;
import mage.game.permanent.token.ThopterColorlessToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TezzeretArtificeMaster extends CardImpl {

    public TezzeretArtificeMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEZZERET);
        this.setStartingLoyalty(5);

        // +1: Create a colorless Thopter artifact creature token with flying.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new ThopterColorlessToken()), 1));

        // 0: Draw a card. If you control three or more artifacts, draw two cards instead.
        this.addAbility(new LoyaltyAbility(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(2),
                new DrawCardSourceControllerEffect(1),
                MetalcraftCondition.instance, "Draw a card. " +
                "If you control three or more artifacts, draw two cards instead"
        ), 0).addHint(MetalcraftHint.instance));

        // −9: You get an emblem with "At the beginning of your end step, search your library for a permanent card, put it into the battlefield, then shuffle your library."
        this.addAbility(new LoyaltyAbility(
                new GetEmblemEffect(new TezzeretArtificeMasterEmblem()), -9
        ));
    }

    private TezzeretArtificeMaster(final TezzeretArtificeMaster card) {
        super(card);
    }

    @Override
    public TezzeretArtificeMaster copy() {
        return new TezzeretArtificeMaster(this);
    }
}

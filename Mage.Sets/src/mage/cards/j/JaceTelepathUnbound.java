package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.*;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.command.emblems.JaceTelepathUnboundEmblem;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class JaceTelepathUnbound extends CardImpl {

    public JaceTelepathUnbound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.JACE);

        this.color.setBlue(true);
        this.nightCard = true;

        this.setStartingLoyalty(5);

        // +1: Up to one target creature gets -2/-0 until your next turn.
        Effect effect = new BoostTargetEffect(-2, 0, Duration.UntilYourNextTurn);
        effect.setText("Up to one target creature gets -2/-0 until your next turn");
        Ability ability = new LoyaltyAbility(effect, 1);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // -3: You may cast target instant or sorcery card from your graveyard this turn. If that card would be put into your graveyard this turn, exile it instead.
        CastCardFromGraveyardThenExileItEffect minusEffect = new CastCardFromGraveyardThenExileItEffect();
        minusEffect.setText("You may cast target instant or sorcery card from your graveyard this turn. If that spell would be put into your graveyard, exile it instead");
        ability = new LoyaltyAbility(minusEffect, -3);
        ability.addTarget(new TargetCardInYourGraveyard(new FilterInstantOrSorceryCard()));
        this.addAbility(ability);

        // −9: You get an emblem with "Whenever you cast a spell, target opponent mills five cards."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new JaceTelepathUnboundEmblem()), -9));
    }

    private JaceTelepathUnbound(final JaceTelepathUnbound card) {
        super(card);
    }

    @Override
    public JaceTelepathUnbound copy() {
        return new JaceTelepathUnbound(this);
    }
}

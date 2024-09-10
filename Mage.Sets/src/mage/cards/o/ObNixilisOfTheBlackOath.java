package mage.cards.o;

import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.LoseLifeOpponentsYouGainLifeLostEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.command.emblems.ObNixilisOfTheBlackOathEmblem;
import mage.game.permanent.token.DemonToken;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ObNixilisOfTheBlackOath extends CardImpl {

    public ObNixilisOfTheBlackOath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NIXILIS);

        this.setStartingLoyalty(3);

        // +2: Each opponent loses 1 life. You gain life equal to the life lost this way.
        this.addAbility(new LoyaltyAbility(new LoseLifeOpponentsYouGainLifeLostEffect(1), 2));

        // -2: Create a 5/5 black Demon creature token with flying. You lose 2 life.
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new CreateTokenEffect(new DemonToken()), -2);
        loyaltyAbility.addEffect(new LoseLifeSourceControllerEffect(2));
        this.addAbility(loyaltyAbility);

        // -8: You get an emblem with "{1}{B}, Sacrifice a creature: You gain X life and draw X cards, where X is the sacrificed creature's power."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new ObNixilisOfTheBlackOathEmblem()), -8));

        // Ob Nixilis of the Black Oath can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private ObNixilisOfTheBlackOath(final ObNixilisOfTheBlackOath card) {
        super(card);
    }

    @Override
    public ObNixilisOfTheBlackOath copy() {
        return new ObNixilisOfTheBlackOath(this);
    }
}

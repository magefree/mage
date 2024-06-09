package mage.cards.t;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.common.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.command.emblems.TeferiHeroOfDominariaEmblem;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TeferiHeroOfDominaria extends CardImpl {

    public TeferiHeroOfDominaria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEFERI);

        this.setStartingLoyalty(4);

        // +1: Draw a card. At the beginning of the next end step, untap up to two lands.
        LoyaltyAbility ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(1), 1);
        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new UntapLandsEffect(2)
        );
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(delayedAbility));
        this.addAbility(ability);

        // −3: Put target nonland permanent into its owner's library third from the top.
        ability = new LoyaltyAbility(new PutIntoLibraryNFromTopTargetEffect(3), -3);
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);

        // −8: You get an emblem with "Whenever you draw a card, exile target permanent an opponent controls."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new TeferiHeroOfDominariaEmblem()), -8));
    }

    private TeferiHeroOfDominaria(final TeferiHeroOfDominaria card) {
        super(card);
    }

    @Override
    public TeferiHeroOfDominaria copy() {
        return new TeferiHeroOfDominaria(this);
    }
}

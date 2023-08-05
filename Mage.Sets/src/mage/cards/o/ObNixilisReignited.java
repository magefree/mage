package mage.cards.o;

import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemTargetPlayerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.command.emblems.ObNixilisReignitedEmblem;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ObNixilisReignited extends CardImpl {

    public ObNixilisReignited(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NIXILIS);

        this.setStartingLoyalty(5);

        // +1: You draw a card and you lose 1 life.
        Effect effect = new DrawCardSourceControllerEffect(1, "you");
        LoyaltyAbility ability1 = new LoyaltyAbility(effect, 1);
        effect = new LoseLifeSourceControllerEffect(1);
        ability1.addEffect(effect.concatBy("and"));
        this.addAbility(ability1);

        // -3: Destroy target creature.
        LoyaltyAbility ability2 = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);

        // -8: Target opponent gets an emblem with "Whenever a player draws a card, you lose 2 life."
        effect = new GetEmblemTargetPlayerEffect(new ObNixilisReignitedEmblem());
        LoyaltyAbility ability3 = new LoyaltyAbility(effect, -8);
        ability3.addTarget(new TargetOpponent());
        this.addAbility(ability3);
    }

    private ObNixilisReignited(final ObNixilisReignited card) {
        super(card);
    }

    @Override
    public ObNixilisReignited copy() {
        return new ObNixilisReignited(this);
    }
}

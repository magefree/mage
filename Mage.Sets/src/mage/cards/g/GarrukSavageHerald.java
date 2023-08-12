package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.DamageAsThoughNotBlockedAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class GarrukSavageHerald extends CardImpl {

    public GarrukSavageHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{G}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GARRUK);
        this.setStartingLoyalty(5);

        // +1: Reveal the top card of your library. If it's a creature card, put it into your hand. Otherwise, put it on the bottom of your library.
        this.addAbility(new LoyaltyAbility(new GarrukSavageHeraldEffect(), 1));

        // −2: Target creature you control deals damage equal to its power to another target creature.
        Effect effect = new DamageWithPowerFromOneToAnotherTargetEffect();
        effect.setText("Target creature you control deals damage equal to its power to another target creature");

        Ability minusAbility = new LoyaltyAbility(effect, -2);
        TargetControlledCreaturePermanent controlledCreature = new TargetControlledCreaturePermanent();
        controlledCreature.setTargetTag(1);
        minusAbility.addTarget(controlledCreature);

        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new AnotherTargetPredicate(2));
        TargetCreaturePermanent anotherTargetCreature = new TargetCreaturePermanent(filter);
        minusAbility.addTarget(anotherTargetCreature.withChooseHint("another creature to deal damage to"));

        this.addAbility(minusAbility);

        // −7: Until end of turn, creatures you control gain "You may have this creature assign its combat damage as though it weren't blocked."
        ContinuousEffect ultimateEffect = new GainAbilityControlledEffect(DamageAsThoughNotBlockedAbility.getInstance(), Duration.EndOfTurn);
        ultimateEffect.setText("Until end of turn, creatures you control gain \"You may have this creature assign its combat damage as though it weren't blocked.\"");
        this.addAbility(new LoyaltyAbility(ultimateEffect, -7));
    }

    private GarrukSavageHerald(final GarrukSavageHerald card) {
        super(card);
    }

    @Override
    public GarrukSavageHerald copy() {
        return new GarrukSavageHerald(this);
    }
}

class GarrukSavageHeraldEffect extends OneShotEffect {

    GarrukSavageHeraldEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top card of your library. If it's a creature card, " +
                "put it into your hand. Otherwise, put it on the bottom of your library";
    }

    private GarrukSavageHeraldEffect(final GarrukSavageHeraldEffect effect) {
        super(effect);
    }

    @Override
    public GarrukSavageHeraldEffect copy() {
        return new GarrukSavageHeraldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.revealCards(source, new CardsImpl(card), game);
        if (card.isCreature(game)) {
            return player.moveCards(card, Zone.HAND, source, game);
        } else {
            return player.putCardsOnBottomOfLibrary(card, game, source, false);
        }
    }
}
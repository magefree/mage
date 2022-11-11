package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ParleyCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.token.CitizenGreenWhiteToken;
import mage.game.permanent.token.Token;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class PhabineBosssConfidant extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Creature tokens");
    static {
        filter.add(TokenPredicate.TRUE);
    }

    public PhabineBosssConfidant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}{W}");

        addSuperType(SuperType.LEGENDARY);
        addSubType(SubType.CAT, SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Creature tokens you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(),
                Duration.WhileOnBattlefield,
                filter)
        ));

        // Parley — At the beginning of combat on your turn, each player reveals the top card of their library.
        //          For each land card revealed this way, you create a 1/1 green and white Citizen creature token.
        //          Then creatures you control get +1/+1 until end of turn for each nonland card revealed this way.
        //          Then each player draws a card.
        Ability parleyAbility = new BeginningOfCombatTriggeredAbility(
                new PhabineBosssConfidantParleyEffect(),
                TargetController.YOU,
                false
        );
        Effect drawCardAllEffect = new DrawCardAllEffect(1);
        drawCardAllEffect.concatBy("Then");
        parleyAbility.addEffect(drawCardAllEffect);
        parleyAbility.setAbilityWord(AbilityWord.PARLEY);
        this.addAbility(parleyAbility);
    }

    private PhabineBosssConfidant(final PhabineBosssConfidant card) {
        super(card);
    }

    @Override
    public PhabineBosssConfidant copy() {
        return new PhabineBosssConfidant(this);
    }
}

class PhabineBosssConfidantParleyEffect extends OneShotEffect {

    PhabineBosssConfidantParleyEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player reveals the top card of their library. " +
                "For each land card revealed this way, you create a 1/1 green and white Citizen creature token. " +
                "Then creatures you control get +1/+1 until end of turn for each nonland card revealed this way.";
    }

    private PhabineBosssConfidantParleyEffect(final PhabineBosssConfidantParleyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int nonLandCount = ParleyCount.getInstance().calculate(game, source, this);
        int nonEmptyLibraries = 0;
        for (UUID playerID : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerID);
            if (player != null && player.getLibrary().size() != 0) {
                nonEmptyLibraries++;
            }
        }
        int landCount = nonEmptyLibraries - nonLandCount;

        if (landCount > 0) {
            Token citizenToken = new CitizenGreenWhiteToken();
            citizenToken.putOntoBattlefield(landCount, game, source, source.getControllerId(), false, false);
            game.applyEffects();
        }

        if (nonLandCount > 0) {
            BoostControlledEffect boostEffect = new BoostControlledEffect(nonLandCount, nonLandCount, Duration.EndOfTurn);
            game.addEffect(boostEffect, source);
        }

        return true;
    }

    @Override
    public PhabineBosssConfidantParleyEffect copy() {
        return new PhabineBosssConfidantParleyEffect(this);
    }
}
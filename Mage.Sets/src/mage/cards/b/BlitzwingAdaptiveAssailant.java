package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LivingMetalAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.util.RandomUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlitzwingAdaptiveAssailant extends CardImpl {

    public BlitzwingAdaptiveAssailant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);
        this.color.setBlack(true);
        this.nightCard = true;

        // Living metal
        this.addAbility(new LivingMetalAbility());

        // At the beginning of combat on your turn, choose flying or indestructible at random. Blitzwing gains that ability until end of turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new BlitzwingCruelTormentorEffect(), TargetController.YOU, false
        ));

        // Whenever Blitzwing deals combat damage to a player, convert it.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new TransformSourceEffect().setText("convert it"), false
        ));
    }

    private BlitzwingAdaptiveAssailant(final BlitzwingAdaptiveAssailant card) {
        super(card);
    }

    @Override
    public BlitzwingAdaptiveAssailant copy() {
        return new BlitzwingAdaptiveAssailant(this);
    }
}

class BlitzwingAdaptiveAssailantEffect extends OneShotEffect {

    BlitzwingAdaptiveAssailantEffect() {
        super(Outcome.Benefit);
        staticText = "choose flying or indestructible at random. {this} gains that ability until end of turn";
    }

    private BlitzwingAdaptiveAssailantEffect(final BlitzwingAdaptiveAssailantEffect effect) {
        super(effect);
    }

    @Override
    public BlitzwingAdaptiveAssailantEffect copy() {
        return new BlitzwingAdaptiveAssailantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Ability ability = RandomUtil.nextBoolean() ? FlyingAbility.getInstance() : IndestructibleAbility.getInstance();
        game.informPlayers(ability.getRule() + " has been chosen");
        game.addEffect(new GainAbilitySourceEffect(ability, Duration.EndOfTurn), source);
        return true;
    }
}

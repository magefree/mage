package mage.cards.t;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class TheScarabGod extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.ZOMBIE, "Zombies you control");
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);
    private static final Hint hint = new ValueHint(
            "Number of Zombies you control", xValue
    );

    public TheScarabGod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // At the beginning of your upkeep, each opponent loses X life and you scry X, where X is the number of Zombies you control.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new LoseLifeOpponentsEffect(xValue).setText("each opponent loses X life"));
        ability.addEffect(new ScryEffect(xValue).concatBy("and you"));
        this.addAbility(ability.addHint(hint));

        // {2}{U}{B}: Exile target creature card from a graveyard. Create a token that's a copy of it, except it's a 4/4 black Zombie.
        ability = new SimpleActivatedAbility(new TheScarabGodExileEffect(), new ManaCostsImpl<>("{2}{U}{B}"));
        ability.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE_A_GRAVEYARD));
        this.addAbility(ability);

        // When The Scarab God dies, return it to its owner's hand at the beginning of the next end step.
        this.addAbility(new DiesSourceTriggeredAbility(new TheScarabGodEffectDieEffect()));
    }

    private TheScarabGod(final TheScarabGod card) {
        super(card);
    }

    @Override
    public TheScarabGod copy() {
        return new TheScarabGod(this);
    }
}

class TheScarabGodExileEffect extends OneShotEffect {

    public TheScarabGodExileEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Exile target creature card from a graveyard. Create a token that's a copy of it, except it's a 4/4 black Zombie.";
    }

    private TheScarabGodExileEffect(final TheScarabGodExileEffect effect) {
        super(effect);
    }

    @Override
    public TheScarabGodExileEffect copy() {
        return new TheScarabGodExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && card != null) {
            controller.moveCards(card, Zone.EXILED, source, game); // Also if the move to exile is replaced, the copy takes place
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, false, 1, false, false, null, 4, 4, false);
            effect.setTargetPointer(new FixedTarget(card, game));
            effect.setOnlySubType(SubType.ZOMBIE);
            effect.setOnlyColor(ObjectColor.BLACK);
            effect.apply(game, source);
            return true;
        }

        return false;
    }
}

class TheScarabGodEffectDieEffect extends OneShotEffect {

    private static final String effectText = "return it to its owner's hand at the beginning of the next end step";

    TheScarabGodEffectDieEffect() {
        super(Outcome.Benefit);
        staticText = effectText;
    }

    private TheScarabGodEffectDieEffect(final TheScarabGodEffectDieEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Create delayed triggered ability
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("return {this} to its owner's hand");
        effect.setTargetPointer(new FixedTarget(source.getSourceId(), source.getStackMomentSourceZCC()));
        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }

    @Override
    public TheScarabGodEffectDieEffect copy() {
        return new TheScarabGodEffectDieEffect(this);
    }
}

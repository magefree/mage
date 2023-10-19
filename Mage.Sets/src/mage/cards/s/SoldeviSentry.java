package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Ketsuban
 */
public final class SoldeviSentry extends CardImpl {

    public SoldeviSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // 1: Choose target opponent. Regenerate Soldevi Sentry. When it regenerates
        // this way, that player may draw a card.
        Ability ability = new SimpleActivatedAbility(new SoldeviSentryRegenerateEffect(), new GenericManaCost(1));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private SoldeviSentry(final SoldeviSentry card) {
        super(card);
    }

    @Override
    public SoldeviSentry copy() {
        return new SoldeviSentry(this);
    }
}

class SoldeviSentryRegenerateEffect extends RegenerateSourceEffect {

    public SoldeviSentryRegenerateEffect() {
        super();
        this.staticText = "Choose target opponent. Regenerate {this}. When it regenerates this way, that player may draw a card";
    }

    protected SoldeviSentryRegenerateEffect(final SoldeviSentryRegenerateEffect effect) {
        super(effect);
    }

    @Override
    public SoldeviSentryRegenerateEffect copy() {
        return new SoldeviSentryRegenerateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        //20110204 - 701.11
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.regenerate(source, game)) {
            game.fireReflexiveTriggeredAbility(
                    new ReflexiveTriggeredAbility(
                            new SoldeviSentryEffect().setTargetPointer(
                                    new FixedTarget(targetPointer.getFirst(game, source), game)
                            ), false
                    ).setTriggerPhrase("When it regenerates this way, "),
                    source
            );
            this.used = true;
            return true;
        }
        return false;
    }
}

class SoldeviSentryEffect extends OneShotEffect {

    SoldeviSentryEffect() {
        super(Outcome.Detriment);
        staticText = "that player may draw a card";
    }

    private SoldeviSentryEffect(final SoldeviSentryEffect effect) {
        super(effect);
    }

    @Override
    public SoldeviSentryEffect copy() {
        return new SoldeviSentryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));

        if (opponent != null) {
            if (opponent.chooseUse(Outcome.DrawCard, "Draw a card?", source, game)) {
                opponent.drawCards(1, source, game);
            }
            return true;
        }
        return false;
    }

}
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.hint.common.MetalcraftHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Loki
 */
public final class ArgentSphinx extends CardImpl {

    public ArgentSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // <i>Metalcraft</i> &mdash; {U}: Exile Argent Sphinx. Return it to the battlefield under your control at the beginning of the next end step. Activate this ability only if you control three or more artifacts.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new ArgentSphinxEffect(),
                new ManaCostsImpl<>("{U}"), MetalcraftCondition.instance
        );
        ability.setAbilityWord(AbilityWord.METALCRAFT);
        ability.addHint(MetalcraftHint.instance);
        this.addAbility(ability);
    }

    private ArgentSphinx(final ArgentSphinx card) {
        super(card);
    }

    @Override
    public ArgentSphinx copy() {
        return new ArgentSphinx(this);
    }

}

class ArgentSphinxEffect extends OneShotEffect {

    private static final String effectText = "Exile {this}. Return it to the battlefield " +
            "under your control at the beginning of the next end step";

    ArgentSphinxEffect() {
        super(Outcome.Benefit);
        staticText = effectText;
    }

    private ArgentSphinxEffect(ArgentSphinxEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        Card card = permanent.getMainCard();
        player.moveCards(permanent, Zone.EXILED, source, game);
        //create delayed triggered ability
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new ReturnToBattlefieldUnderYourControlTargetEffect().setText(
                        "Return it to the battlefield under your control at the beginning of the next end step"
                ).setTargetPointer(new FixedTarget(card, game))
        ), source);
        return true;
    }

    @Override
    public ArgentSphinxEffect copy() {
        return new ArgentSphinxEffect(this);
    }
}

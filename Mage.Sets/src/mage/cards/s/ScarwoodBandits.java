package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;
import mage.util.CardUtil;

import java.util.UUID;
import mage.abilities.condition.common.SourceRemainsInZoneCondition;

/**
 * @author L_J
 */
public final class ScarwoodBandits extends CardImpl {

    public ScarwoodBandits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Forestwalk
        this.addAbility(new ForestwalkAbility());

        // {2}{G}, {tap}: Unless an opponent pays {2}, gain control of target artifact for as long as Scarwood Bandits remains on the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DoUnlessAnyOpponentPaysEffect(
                        new ConditionalContinuousEffect(
                                new GainControlTargetEffect(Duration.Custom, true),
                                new SourceRemainsInZoneCondition(Zone.BATTLEFIELD),
                                "gain control of target artifact for as long as {this} remains on the battlefield"),
                        new GenericManaCost(2)),
                new ManaCostsImpl<>("{2}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private ScarwoodBandits(final ScarwoodBandits card) {
        super(card);
    }

    @Override
    public ScarwoodBandits copy() {
        return new ScarwoodBandits(this);
    }
}

class DoUnlessAnyOpponentPaysEffect extends OneShotEffect {

    protected Effects executingEffects = new Effects();
    private final Cost cost;
    private String chooseUseText;

    public DoUnlessAnyOpponentPaysEffect(Effect effect, Cost cost) {
        this(effect, cost, null);
    }

    public DoUnlessAnyOpponentPaysEffect(Effect effect, Cost cost, String chooseUseText) {
        super(Outcome.Benefit);
        this.executingEffects.add(effect);
        this.cost = cost;
        this.chooseUseText = chooseUseText;
    }

    public DoUnlessAnyOpponentPaysEffect(final DoUnlessAnyOpponentPaysEffect effect) {
        super(effect);
        this.executingEffects = effect.executingEffects.copy();
        this.cost = effect.cost.copy();
        this.chooseUseText = effect.chooseUseText;
    }

    public void addEffect(Effect effect) {
        executingEffects.add(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            String message;
            if (chooseUseText == null) {
                String effectText = executingEffects.getText(source.getModes().getMode());
                message = "Pay " + cost.getText() + " to prevent (" + effectText.substring(0, effectText.length() - 1) + ")?";
            } else {
                message = chooseUseText;
            }
            message = CardUtil.replaceSourceName(message, sourceObject.getName());
            boolean result = true;
            boolean doEffect = true;
            // check if any opponent is willing to pay
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && player.canRespond()
                        && !player.equals(controller)
                        && cost.canPay(source, source, player.getId(), game)
                        && player.chooseUse(Outcome.Benefit, message, source, game)) {
                    cost.clearPaid();
                    if (cost.pay(source, game, source, player.getId(), false, null)) {
                        if (!game.isSimulation()) {
                            game.informPlayers(player.getLogName() + " pays the cost to prevent the effect");
                        }
                        doEffect = false;
                        break;
                    }
                }
            }
            // do the effects if nobody paid
            if (doEffect) {
                for (Effect effect : executingEffects) {
                    effect.setTargetPointer(this.targetPointer);
                    if (effect instanceof OneShotEffect) {
                        result &= effect.apply(game, source);
                    } else {
                        game.addEffect((ContinuousEffect) effect, source);
                    }
                }
            }
            return result;
        }
        return false;
    }

    protected Player getPayingPlayer(Game game, Ability source) {
        return game.getPlayer(source.getControllerId());
    }

    @Override
    public String getText(Mode mode) {
        String effectsText = executingEffects.getText(mode);
        return effectsText.substring(0, effectsText.length() - 1) + " unless any opponent pays " + cost.getText();
    }

    @Override
    public DoUnlessAnyOpponentPaysEffect copy() {
        return new DoUnlessAnyOpponentPaysEffect(this);
    }
}

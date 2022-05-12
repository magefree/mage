package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;
import mage.util.ManaUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Eirkei
 */
public class SacrificeOpponentsUnlessPayEffect extends OneShotEffect {

    protected Cost cost;
    protected DynamicValue genericMana;
    protected DynamicValue amount;
    protected FilterPermanent filter;

    public SacrificeOpponentsUnlessPayEffect(Cost cost) {
        this(cost, new FilterPermanent(), 1);
    }

    public SacrificeOpponentsUnlessPayEffect(int genericManaCost) {
        this(genericManaCost, new FilterPermanent(), 1);
    }

    public SacrificeOpponentsUnlessPayEffect(Cost cost, FilterPermanent filter) {
        this(cost, filter, 1);
    }

    public SacrificeOpponentsUnlessPayEffect(int genericManaCost, FilterPermanent filter) {
        this(genericManaCost, filter, 1);
    }

    public SacrificeOpponentsUnlessPayEffect(Cost cost, FilterPermanent filter, int amount) {
        this(cost, filter, StaticValue.get(amount));
    }

    public SacrificeOpponentsUnlessPayEffect(int genericManaCost, FilterPermanent filter, int amount) {
        this(new GenericManaCost(genericManaCost), filter, StaticValue.get(amount));
    }

    public SacrificeOpponentsUnlessPayEffect(Cost cost, FilterPermanent filter, DynamicValue amount) {
        super(Outcome.Sacrifice);
        this.cost = cost;
        this.amount = amount;
        this.filter = filter;
    }

    public SacrificeOpponentsUnlessPayEffect(DynamicValue genericMana, FilterPermanent filter, DynamicValue amount) {
        super(Outcome.Sacrifice);
        this.genericMana = genericMana;
        this.amount = amount;
        this.filter = filter;
    }

    public SacrificeOpponentsUnlessPayEffect(final SacrificeOpponentsUnlessPayEffect effect) {
        super(effect);
        if (effect.cost != null) {
            this.cost = effect.cost.copy();
        }

        if (effect.genericMana != null) {
            this.genericMana = effect.genericMana.copy();
        }

        if (effect.amount != null) {
            this.amount = effect.amount.copy();
        }

        if (effect.filter != null) {
            this.filter = effect.filter.copy();
        }
    }

    @Override
    public SacrificeOpponentsUnlessPayEffect copy() {
        return new SacrificeOpponentsUnlessPayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> permsToSacrifice = new ArrayList<>();
        filter.add(TargetController.YOU.getControllerPredicate());

        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);

            if (player != null) {
                Cost costToPay;
                String costValueMessage;
                if (cost != null) {
                    costToPay = cost.copy();
                    costValueMessage = costToPay.getText();
                } else {
                    costToPay = ManaUtil.createManaCost(genericMana, game, source, this);
                    costValueMessage = "{" + genericMana.calculate(game, source, this) + "}";
                }
                String message = "";
                if (costToPay instanceof ManaCost) {
                    message += "Pay ";
                }
                message += costValueMessage + '?';

                costToPay.clearPaid();
                if (!(player.chooseUse(Outcome.Benefit, message, source, game)
                        && costToPay.pay(source, game, source, player.getId(), false, null))) {
                    game.informPlayers(player.getLogName() + " chooses not to pay " + costValueMessage + " to prevent the sacrifice effect");

                    int numTargets = Math.min(amount.calculate(game, source, this), game.getBattlefield().countAll(filter, player.getId(), game));
                    if (numTargets > 0) {
                        TargetPermanent target = new TargetPermanent(numTargets, numTargets, filter, true);

                        if (target.canChoose(player.getId(), source, game)) {
                            player.chooseTarget(Outcome.Sacrifice, target, source, game);
                            permsToSacrifice.addAll(target.getTargets());
                        }
                    }
                } else {
                    game.informPlayers(player.getLogName() + " chooses to pay " + costValueMessage + " to prevent the sacrifice effect");
                }
            }
        }

        for (UUID permID : permsToSacrifice) {
            Permanent permanent = game.getPermanent(permID);

            if (permanent != null) {
                permanent.sacrifice(source, game);
            }
        }

        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("each opponent sacrifices ");

        switch (amount.toString()) {
            case "1":
                sb.append(CardUtil.addArticle(filter.getMessage()));
                break;
            case "X":
                sb.append("X ");
                sb.append(filter.getMessage());
                break;
            default:
                sb.append(CardUtil.numberToText(amount.toString()));
                sb.append(' ');
                sb.append(filter.getMessage());
        }


        sb.append(" unless they ");

        if (cost != null) {
            sb.append(CardUtil.addCostVerb(cost.getText()));
        } else {
            sb.append("pay {X}");
        }

        if (genericMana != null && !genericMana.getMessage().isEmpty()) {
            sb.append(", where X is ");
            sb.append(genericMana.getMessage());
        }

        return sb.toString();
    }
}

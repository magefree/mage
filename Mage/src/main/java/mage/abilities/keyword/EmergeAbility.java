package mage.abilities.keyword;

import mage.Mana;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.mana.ManaOptions;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author emerald000
 */
public class EmergeAbility extends SpellAbility {

    private final ManaCosts<ManaCost> emergeCost;

    public EmergeAbility(Card card, ManaCosts<ManaCost> emergeCost) {
        super(card.getSpellAbility());
        this.emergeCost = emergeCost.copy();
        this.newId(); // Set newId because cards spell ability is copied and needs own id
        this.setCardName(card.getName() + " with emerge");
        zone = Zone.HAND;
        spellAbilityType = SpellAbilityType.BASE_ALTERNATE;

        this.getManaCosts().clear();
        this.getManaCostsToPay().clear();
        this.addManaCost(emergeCost.copy());

        this.setRuleAtTheTop(true);        
    }

    public EmergeAbility(final EmergeAbility ability) {
        super(ability);
        this.emergeCost = ability.emergeCost.copy();
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        if (super.canActivate(playerId, game).canActivate()) {
            Player controller = game.getPlayer(this.getControllerId());
            if (controller != null) {
                for (Permanent creature : game.getBattlefield().getActivePermanents(
                        new FilterControlledCreaturePermanent(), this.getControllerId(), this, game)) {
                    ManaCost costToPay = CardUtil.reduceCost(emergeCost.copy(), creature.getManaValue());
                    if (costToPay.canPay(this, this, this.getControllerId(), game)) {
                        return ActivationStatus.getTrue(this, game);
                    }
                }
            }
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public ManaOptions getMinimumCostToActivate(UUID playerId, Game game) {
        int maxCMC = 0;
        for (Permanent creature : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), playerId, this, game)) {
            int cmc = creature.getManaValue();
            if (cmc > maxCMC) {
                maxCMC = cmc;
            }
        }
        ManaOptions manaOptions = super.getMinimumCostToActivate(playerId, game);
        for (Mana mana : manaOptions) {
            if (mana.getGeneric() > maxCMC) {
                mana.setGeneric(mana.getGeneric() - maxCMC);
            } else {
                mana.setGeneric(0);
            }
        }
        return manaOptions;
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        Player controller = game.getPlayer(this.getControllerId());
        if (controller != null) {
            TargetPermanent target = new TargetControlledCreaturePermanent(new FilterControlledCreaturePermanent("creature to sacrifice for emerge"));
            if (controller.choose(Outcome.Sacrifice, target, this, game)) {
                Permanent creature = game.getPermanent(target.getFirstTarget());
                if (creature != null) {
                    CardUtil.reduceCost(this, creature.getManaValue());
                    if (super.activate(game, false)) {
                        if (creature.sacrifice(this, game)) {
                            return true;
                        } else {
                            activated = false;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public EmergeAbility copy() {
        return new EmergeAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return "Emerge " + emergeCost.getText() + " <i>(You may cast this spell by sacrificing a creature and paying the emerge cost reduced by that creature's mana value.)</i>";
    }
}

package mage.abilities.keyword;

import mage.ApprovingObject;
import mage.MageObjectReference;
import mage.Mana;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.mana.ManaOptions;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author emerald000
 */
public class EmergeAbility extends SpellAbility {

    private final ManaCosts<ManaCost> emergeCost;
    public static final String EMERGE_ACTIVATION_CREATURE_REFERENCE = "emergeActivationMOR";

    private final String emergeFromText;
    private final FilterPermanent filter;

    public EmergeAbility(Card card, String emergeManaString) {
        this(card, emergeManaString, StaticFilters.FILTER_PERMANENT_CREATURE, "");
    }

    public EmergeAbility(Card card, String emergeManaString, FilterPermanent filter, String emergeFromText) {
        super(card.getSpellAbility());

        this.filter = TargetSacrifice.makeFilter(filter);
        this.emergeFromText = emergeFromText;

        this.emergeCost = new ManaCostsImpl<>(emergeManaString);
        this.newId(); // Set newId because cards spell ability is copied and needs own id
        this.setCardName(card.getName() + " with emerge");
        this.zone = Zone.HAND;
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;

        this.clearManaCosts();
        this.clearManaCostsToPay();
        this.addCost(emergeCost.copy());

        this.setRuleAtTheTop(true);
    }

    private EmergeAbility(final EmergeAbility ability) {
        super(ability);
        this.emergeCost = ability.emergeCost.copy();
        this.filter = ability.filter;
        this.emergeFromText = ability.emergeFromText;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        if (super.canActivate(playerId, game).canActivate()) {
            Player controller = game.getPlayer(this.getControllerId());
            if (controller != null) {
                for (Permanent creature : game.getBattlefield().getActivePermanents(
                        filter, this.getControllerId(), this, game)) {
                    ManaCost costToPay = CardUtil.reduceCost(emergeCost.copy(), creature.getManaValue());
                    if (costToPay.canPay(this, this, this.getControllerId(), game)) {
                        return new ActivationStatus(new ApprovingObject(this, game));
                    }
                }
            }
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public ManaOptions getMinimumCostToActivate(UUID playerId, Game game) {
        int maxCMC = 0;
        for (Permanent permanentToSacrifice : game.getBattlefield().getActivePermanents(filter, playerId, this, game)) {
            int cmc = permanentToSacrifice.getManaValue();
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
            TargetSacrifice target = new TargetSacrifice(filter);
            target.withChooseHint("to sacrifice for emerge");
            if (controller.choose(Outcome.Sacrifice, target, this, game)) {
                Permanent creature = game.getPermanent(target.getFirstTarget());
                if (creature != null) {
                    CardUtil.reduceCost(this, creature.getManaValue());
                    if (super.activate(game, false)) {
                        MageObjectReference mor = new MageObjectReference(creature, game);
                        if (creature.sacrifice(this, game)) {
                            this.setCostsTag(EMERGE_ACTIVATION_CREATURE_REFERENCE, mor); //Can access with LKI afterwards
                            return true;
                        } else {
                            activated = false; // TODO: research, why
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
        String text = "Emerge ";
        if (!emergeFromText.isEmpty()) {
            text += emergeFromText + " ";
        }
        text += emergeCost.getText();
        if (emergeFromText.isEmpty()) {
            text += " <i>(You may cast this spell by sacrificing a creature and paying the emerge cost reduced by that creature's mana value.)</i>";
        }
        return text;
    }
}

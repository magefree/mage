package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Johnny E. Hastings
 */
public final class SoulBurn extends CardImpl {

    private static final FilterMana filterBlackOrRed = new FilterMana();

    static {
        filterBlackOrRed.setBlack(true);
        filterBlackOrRed.setRed(true);
    }

    public SoulBurn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{2}{B}");

        // Spend only black or red mana on X.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new InfoEffect("Spend only black or red mana on X")).setRuleAtTheTop(true)
        );

        // Soul Burn deals X damage to any target. You gain life equal to the damage dealt for each black mana spent on X; not more life than the player's life total before Soul Burn dealt damage, or the creature's toughness.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new SoulBurnEffect());
        VariableCost variableCost = this.getSpellAbility().getManaCostsToPay().getVariableCosts().get(0);
        if (variableCost instanceof VariableManaCost) {
            ((VariableManaCost) variableCost).setFilter(filterBlackOrRed);
        }
    }

    private SoulBurn(final SoulBurn card) {
        super(card);
    }

    @Override
    public SoulBurn copy() {
        return new SoulBurn(this);
    }
}

class SoulBurnEffect extends OneShotEffect {

    SoulBurnEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals X damage to any target. You gain life equal to the damage dealt, " +
                "but not more than the amount of {B} spent on X, the player's life total before the damage was dealt, " +
                "the planeswalker's loyalty before the damage was dealt, or the creature's toughness.";
    }

    private SoulBurnEffect(final SoulBurnEffect effect) {
        super(effect);
    }

    /***
     * @param game
     * @param source
     * @return
     */
    @Override
    public boolean apply(Game game, Ability source) {

        // Get the colors we care about. (This isn't racist, honestly.)
        int amountBlack = source.getManaCostsToPay().getUsedManaToPay().getBlack();
        int amountRed = source.getManaCostsToPay().getUsedManaToPay().getRed();

        // Get the colors we don't really care about. (See note above.)
        int amountWhite = source.getManaCostsToPay().getUsedManaToPay().getWhite();
        int amountGreen = source.getManaCostsToPay().getUsedManaToPay().getGreen();
        int amountBlue = source.getManaCostsToPay().getUsedManaToPay().getBlue();
        int amountColorless = source.getManaCostsToPay().getUsedManaToPay().getColorless();

        // Figure out what was spent on the spell in total, determine proper values for 
        // black and red, minus initial casting cost. 
        int totalColorlessForCastingCost = amountWhite + amountGreen + amountBlue + amountColorless;
        int amountOffsetByColorless = 0;
        if (totalColorlessForCastingCost > 0) {
            amountOffsetByColorless = totalColorlessForCastingCost;
            if (amountOffsetByColorless > 2) {
                // The game should never let this happen, but I'll check anyway since I don't know
                // the guts of the game [yet]. 
                amountOffsetByColorless = 2;
            }
        }

        // Remove 1 black to account for casting cost. 
        amountBlack--;

        // Determine if we need to offset the red or black values any further due to the 
        // amount of non-red and non-black paid. 
        if (amountOffsetByColorless < 2) {
            int amountToOffsetBy = 2 - amountOffsetByColorless;

            if (amountRed > 0) {
                if (amountRed >= amountToOffsetBy) {
                    // Pay all additional unpaid casting cost with red.
                    amountRed = amountRed - amountToOffsetBy;
                } else {
                    // Red paid doesn't cover the 2 default required by the spell.
                    // Pay some in red, and some in black. 
                    // If we're here, red is 1, and amountToOffetBy is 2. 
                    // That means we can subtract 1 from both red and black.
                    amountRed--;
                    amountBlack--;
                }
            } else {
                // Pay all additional unpaid casting cost with black.
                amountBlack = amountBlack - amountToOffsetBy;
            }
        }

        int totalXAmount = amountBlack + amountRed;

        int lifetogain = amountBlack;
        if (totalXAmount == 0) {
            return false;
        }
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            if (permanent.isCreature(game)) {
                lifetogain = Math.min(permanent.getToughness().getValue(), lifetogain);
            } else if (permanent.isPlaneswalker(game)) {
                lifetogain = Math.min(permanent.getCounters(game).getCount(CounterType.LOYALTY), lifetogain);
            } else {
                return false;
            }
            permanent.damage(totalXAmount, source.getSourceId(), source, game);
        } else {
            Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (player == null) {
                return false;
            }
            lifetogain = Math.min(player.getLife(), lifetogain);
            player.damage(totalXAmount, source.getSourceId(), source, game);
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.gainLife(lifetogain, game, source);
        return true;
    }

    @Override
    public SoulBurnEffect copy() {
        return new SoulBurnEffect(this);
    }

}

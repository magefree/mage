
package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.*;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.hint.common.BargainCostWasPaidHint;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * Written before ruling was clarified. Feel free to put the ruling once it gets there.
 * <p>
 * Bargain is a keyword static ability that adds an optional additional cost.
 * <p>
 * Bargain means "You may sacrifice an artifact, enchantment, or token as you cast this spell".
 * <p>
 * If a spell bargain cost is paid, the spell or the permanent it becomes is bargained.
 *
 * @author Susucr
 */
public class BargainAbility extends StaticAbility implements OptionalAdditionalSourceCosts {

    private static final FilterControlledPermanent bargainFilter = new FilterControlledPermanent("an artifact, enchantment, or token");
    private static final String promptString = "Bargain? (To Bargain, sacrifice an artifact, enchantment, or token)";
    private static final String keywordText = "Bargain";
    private static final String reminderText = "You may sacrifice an artifact, enchantment, or token as you cast this spell.";
    private final String rule;

    private boolean pastBargaining;
    private boolean choseToNotBargain;

    private String activationKey; // TODO: replace by Tag Cost Tracking.

    protected OptionalAdditionalCost additionalCost;

    static {
        bargainFilter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                TokenPredicate.TRUE
        ));
    }

    public BargainAbility() {
        super(Zone.STACK, null);
        this.additionalCost = new OptionalAdditionalCostImpl(keywordText, reminderText, new SacrificeTargetCost(bargainFilter));
        this.additionalCost.setRepeatable(false);
        this.rule = additionalCost.getName() + additionalCost.getReminderText();
        this.setRuleAtTheTop(true);
        this.addHint(BargainCostWasPaidHint.instance);
        this.activationKey = null;
        this.pastBargaining = false;
        this.choseToNotBargain = false;
    }

    private BargainAbility(final BargainAbility ability) {
        super(ability);
        this.rule = ability.rule;
        this.additionalCost = ability.additionalCost.copy();
        this.activationKey = ability.activationKey;
        this.pastBargaining = ability.pastBargaining;
        this.choseToNotBargain = ability.choseToNotBargain;
    }

    @Override
    public BargainAbility copy() {
        return new BargainAbility(this);
    }

    public void resetBargain() {
        if (additionalCost != null) {
            additionalCost.reset();
        }
        this.activationKey = null;
        this.pastBargaining = false;
        this.choseToNotBargain = false;
    }

    @Override
    public void addOptionalAdditionalCosts(Ability ability, Game game) {
        if (!(ability instanceof SpellAbility)) {
            return;
        }

        Player player = game.getPlayer(ability.getControllerId());
        if (player == null) {
            return;
        }

        this.resetBargain();
        boolean canPay = additionalCost.canPay(ability, this, ability.getControllerId(), game);
        boolean chooseToBargain = canPay && player.chooseUse(Outcome.Sacrifice, promptString, ability, game);
        pastBargaining = true;
        if (!chooseToBargain) {
            choseToNotBargain = true;
            return;
        }

        additionalCost.activate();
        for (Cost cost : ((Costs<Cost>) additionalCost)) {
            ability.getCosts().add(cost.copy());
        }
        this.activationKey = getActivationKey(ability, game);
    }

    @Override
    public String getCastMessageSuffix() {
        return additionalCost.getCastSuffixMessage(0);
    }

    public boolean wasBargained(Game game, Ability source) {
        return activationKey != null && getActivationKey(source, game).equalsIgnoreCase(activationKey);
    }

    // Returns true if the bargain cost is activated.
    public boolean isBargaining(Game game, Ability source) {
        return pastBargaining;
    }

    public boolean choseToNotBargain(Game game, Ability source) {
        return choseToNotBargain;
    }


    /**
     * TODO: remove with Tag Cost Tracking.
     * Return activation zcc key for searching spell's settings in source object
     *
     * @param source
     * @param game
     */
    public static String getActivationKey(Ability source, Game game) {
        // Bargain activates in STACK zone so all zcc must be from "stack moment"
        // Use cases:
        // * resolving spell have same zcc (example: check kicker status in sorcery/instant);
        // * copied spell have same zcc as source spell (see Spell.copySpell and zcc sync);
        // * creature/token from resolved spell have +1 zcc after moved to battlefield (example: check kicker status in ETB triggers/effects);

        // find object info from the source ability (it can be a permanent or a spell on stack, on the moment of trigger/resolve)
        MageObject sourceObject = source.getSourceObject(game);
        Zone sourceObjectZone = game.getState().getZone(sourceObject.getId());
        int zcc = CardUtil.getActualSourceObjectZoneChangeCounter(game, source);

        // find "stack moment" zcc:
        // * permanent cards enters from STACK to BATTLEFIELD (+1 zcc)
        // * permanent tokens enters from OUTSIDE to BATTLEFIELD (+1 zcc, see prepare code in TokenImpl.putOntoBattlefieldHelper)
        // * spells and copied spells resolves on STACK (zcc not changes)
        if (sourceObjectZone != Zone.STACK) {
            --zcc;
        }

        return zcc + "";
    }

    @Override
    public String getRule() {
        return rule;
    }
}
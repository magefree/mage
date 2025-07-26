package mage.abilities.keyword;

import mage.MageIdentifier;
import mage.abilities.SpellAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;

import java.util.Set;

/**
 * @author TheElk801
 */
public class WebSlingingAbility extends SpellAbility {

    public static final String WEB_SLINGING_ACTIVATION_VALUE_KEY = "webSlingingActivation";
    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("tapped creature you control");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public WebSlingingAbility(Card card, String manaString) {
        super(card.getSpellAbility());
        this.newId();
        this.setCardName(card.getName() + " with Web-slinging");
        zone = Zone.HAND;
        spellAbilityType = SpellAbilityType.BASE_ALTERNATE;

        this.clearManaCosts();
        this.clearManaCostsToPay();
        this.addCost(new ManaCostsImpl<>(manaString));
        this.addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter)));

        this.setRuleAtTheTop(true);
    }

    protected WebSlingingAbility(final WebSlingingAbility ability) {
        super(ability);
    }

    @Override
    public boolean activate(Game game, Set<MageIdentifier> allowedIdentifiers, boolean noMana) {
        if (!super.activate(game, allowedIdentifiers, noMana)) {
            return false;
        }
        this.setCostsTag(WEB_SLINGING_ACTIVATION_VALUE_KEY, null);
        return true;
    }

    @Override
    public WebSlingingAbility copy() {
        return new WebSlingingAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Web-slinging ");
        sb.append(getManaCosts().getText());
        sb.append(" <i>(You may cast this spell for ");
        sb.append(getManaCosts().getText());
        sb.append(" if you also return a tapped creature you control to its owner's hand.)</i>");
        return sb.toString();
    }
}

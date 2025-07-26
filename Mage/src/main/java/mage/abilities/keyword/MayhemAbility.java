package mage.abilities.keyword;

import mage.ApprovingObject;
import mage.MageIdentifier;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class MayhemAbility extends SpellAbility {

    public static final String MAYHEM_ACTIVATION_VALUE_KEY = "mayhemActivation";

    private final String rule;

    public MayhemAbility(Card card, String manaString) {
        super(card.getSpellAbility());
        this.newId();
        this.setCardName(card.getName() + " with Mayhem");
        zone = Zone.GRAVEYARD;
        spellAbilityType = SpellAbilityType.BASE_ALTERNATE;

        this.clearManaCosts();
        this.clearManaCostsToPay();
        this.addCost(new ManaCostsImpl<>(manaString));

        this.setRuleAtTheTop(true);
        this.rule = "Surge " + manaString +
                " <i>(You may cast this card from your graveyard for "+manaString+
                " if you discarded it this turn. Timing rules still apply.)</i>";
    }

    protected MayhemAbility(final MayhemAbility ability) {
        super(ability);
        this.rule = ability.rule;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        // TODO: Implement this
        return super.canActivate(playerId,game);
    }

    @Override
    public boolean activate(Game game, Set<MageIdentifier> allowedIdentifiers, boolean noMana) {
        if (!super.activate(game, allowedIdentifiers, noMana)) {
            return false;
        }
        this.setCostsTag(MAYHEM_ACTIVATION_VALUE_KEY, null);
        return true;
    }

    @Override
    public MayhemAbility copy() {
        return new MayhemAbility(this);
    }

    @Override
    public String getRule() {
        return rule;
    }

}

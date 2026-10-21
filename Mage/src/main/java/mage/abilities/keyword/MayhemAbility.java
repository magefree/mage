package mage.abilities.keyword;

import mage.MageIdentifier;
import mage.MageObjectReference;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.constants.SpellAbilityType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DiscardedCardsEvent;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
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
        this.addWatcher(new MayhemWatcher());
        this.setRuleAtTheTop(true);
        this.rule = "Mayhem " + manaString +
                " <i>(You may cast this card from your graveyard for " + manaString +
                " if you discarded it this turn. Timing rules still apply.)</i>";
    }

    protected MayhemAbility(final MayhemAbility ability) {
        super(ability);
        this.rule = ability.rule;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        if (!Zone.GRAVEYARD.match(game.getState().getZone(getSourceId()))
                || !MayhemWatcher.checkCard(getSourceId(), game)) {
            return ActivationStatus.getFalse();
        }
        return super.canActivate(playerId, game);
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

class MayhemWatcher extends Watcher {

    private final Set<MageObjectReference> set = new HashSet<>();

    MayhemWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DISCARDED_CARDS) {
            return;
        }
        for (Card card : ((DiscardedCardsEvent) event).getDiscardedCards().getCards(game)) {
            set.add(new MageObjectReference(card, game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
    }

    static boolean checkCard(UUID cardId, Game game) {
        return game
                .getState()
                .getWatcher(MayhemWatcher.class)
                .set
                .stream()
                .anyMatch(mor -> mor.refersTo(cardId, game));
    }
}

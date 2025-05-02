package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.FlurryAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainSuspendEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author Jmlundeen
 */
public final class TaigamMasterOpportunist extends CardImpl {

    public TaigamMasterOpportunist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flurry -- Whenever you cast your second spell each turn, copy it, then exile the spell you cast with four time counters on it. If it doesn't have suspend, it gains suspend.
        this.addAbility(new FlurryAbility(new TaigamMasterOpportunistEffect()));
    }

    private TaigamMasterOpportunist(final TaigamMasterOpportunist card) {
        super(card);
    }

    @Override
    public TaigamMasterOpportunist copy() {
        return new TaigamMasterOpportunist(this);
    }
}

class TaigamMasterOpportunistEffect extends OneShotEffect {

    public TaigamMasterOpportunistEffect() {
        super(Outcome.Copy);
        this.staticText = "copy it, then exile the spell you cast with four time counters on it. " +
                "If it doesn't have suspend, it gains suspend.";
    }

    public TaigamMasterOpportunistEffect(final TaigamMasterOpportunistEffect effect) {
        super(effect);
    }

    @Override
    public TaigamMasterOpportunistEffect copy() {
        return new TaigamMasterOpportunistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) this.getValue("spellCast");
        Player controller = game.getPlayer(source.getControllerId());
        if (spell == null || controller == null) {
            return false;
        }

        // copy it
        spell.createCopyOnStack(game, source, source.getControllerId(), false);
        // exile it, if it doesn't have suspend, it gains suspend
        // get main card to work with adventure/omen/split
        Card card = spell.getMainCard();
        if (card == null) {
            return false;
        }
        UUID exileId = SuspendAbility.getSuspendExileId(controller.getId(), game);
        if (controller.moveCardsToExile(card, source, game, true, exileId, "Suspended cards of " + controller.getName())) {
            boolean hasSuspend = card.getAbilities(game).containsClass(SuspendAbility.class);
            card.addCounters(CounterType.TIME.createInstance(4), source, game);
            game.informPlayers(controller.getLogName() + " exiles " + spell.getLogName() + " with 3 time counters on it");
            if (!hasSuspend) {
                game.addEffect(new GainSuspendEffect(new MageObjectReference(card, game)), source);
            }
            return true;
        }
        return false;
    }
}
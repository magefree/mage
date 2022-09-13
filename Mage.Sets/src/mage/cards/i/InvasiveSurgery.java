package mage.cards.i;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 * @author LevelX2
 */
public final class InvasiveSurgery extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("sorcery spell");

    static {
        filter.add(CardType.SORCERY.getPredicate());
    }

    public InvasiveSurgery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Counter target sorcery spell.
        // <i>Delirium</i> &mdash; If there are four or more card types among cards in your graveyard, search the graveyard, hand, and library of that spell's controller for any number of cards with the same name as that spell, exile those cards, then that player shuffles their library.
        this.getSpellAbility().addEffect(new InvasiveSurgeryEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addHint(CardTypesInGraveyardHint.YOU);
    }

    private InvasiveSurgery(final InvasiveSurgery card) {
        super(card);
    }

    @Override
    public InvasiveSurgery copy() {
        return new InvasiveSurgery(this);
    }
}

class InvasiveSurgeryEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    public InvasiveSurgeryEffect() {
        super(true, "that spell's controller", "all cards with the same name as that spell");
        this.staticText = "Counter target sorcery spell.<br><br>"
                + "<i>Delirium</i> &mdash; If there are four or more card types among cards in your graveyard, "
                + "search the graveyard, hand, and library of that spell's controller for any number of cards "
                + "with the same name as that spell, exile those cards, then that player shuffles";
    }

    public InvasiveSurgeryEffect(final InvasiveSurgeryEffect effect) {
        super(effect);
    }

    @Override
    public InvasiveSurgeryEffect copy() {
        return new InvasiveSurgeryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        String cardName = "";
        UUID spellController = null;
        if (source.getTargets().get(0) instanceof TargetSpell) {
            UUID objectId = source.getFirstTarget();
            StackObject stackObject = game.getStack().getStackObject(objectId);
            if (stackObject != null) {
                MageObject targetObject = game.getObject(stackObject.getSourceId());
                if (targetObject instanceof Card) {
                    cardName = targetObject.getName();
                }
                spellController = stackObject.getControllerId();
                game.getStack().counter(objectId, source, game);
            }
        }

        // Check the Delirium condition
        if (!DeliriumCondition.instance.apply(game, source)) {
            return true;
        }
        return this.applySearchAndExile(game, source, cardName, spellController);
    }
}

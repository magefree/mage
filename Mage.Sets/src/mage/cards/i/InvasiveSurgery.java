package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

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

    InvasiveSurgeryEffect() {
        super(true, "that spell's controller", "all cards with the same name as that spell");
        this.staticText = "Counter target sorcery spell.<br><br>"
                + "<i>Delirium</i> &mdash; If there are four or more card types among cards in your graveyard, "
                + "search the graveyard, hand, and library of that spell's controller for any number of cards "
                + "with the same name as that spell, exile those cards, then that player shuffles";
    }

    private InvasiveSurgeryEffect(final InvasiveSurgeryEffect effect) {
        super(effect);
    }

    @Override
    public InvasiveSurgeryEffect copy() {
        return new InvasiveSurgeryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = game.getSpell(getTargetPointer().getFirst(game, source));
        if (controller == null || spell == null) {
            return false;
        }
        game.getStack().counter(spell.getId(), source, game);
        return !DeliriumCondition.instance.apply(game, source)
                || this.applySearchAndExile(game, source, spell, spell.getControllerId());
    }
}

package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SevinnesReclamation extends CardImpl {

    private static final FilterCard filter
            = new FilterPermanentCard("permanent card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public SevinnesReclamation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Return target permanent card with converted mana cost 3 or less from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
        // If this spell was cast from a graveyard, you may copy this spell and may choose a new target for the copy.
        this.getSpellAbility().addEffect(new SevinnesReclamationEffect());

        // Flashback {4}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{4}{W}")));
    }

    private SevinnesReclamation(final SevinnesReclamation card) {
        super(card);
    }

    @Override
    public SevinnesReclamation copy() {
        return new SevinnesReclamation(this);
    }
}

class SevinnesReclamationEffect extends OneShotEffect {

    SevinnesReclamationEffect() {
        super(Outcome.Benefit);
        staticText = "If this spell was cast from a graveyard, " +
                "you may copy this spell and may choose a new target for the copy.";
    }

    private SevinnesReclamationEffect(final SevinnesReclamationEffect effect) {
        super(effect);
    }

    @Override
    public SevinnesReclamationEffect copy() {
        return new SevinnesReclamationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // If a spell is a copy it wasn't cast from the graveyard.
        Spell spell = game.getStack().getSpell(source.getSourceId(), false);
        Player player = game.getPlayer(source.getControllerId());
        if (spell == null || player == null) {
            return false;
        }
        if (spell.getFromZone() == Zone.GRAVEYARD
                && player.chooseUse(outcome, "Copy this spell?", source, game)) {
            spell.createCopyOnStack(game, source, source.getControllerId(), true);
        }
        return true;
    }
}

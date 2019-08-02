package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
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
            = new FilterPermanentCard("permanent card with converted mana cost 3 or less from your graveyard");

    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 4));
    }

    public SevinnesReclamation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Return target permanent card with converted mana cost 3 or less from your graveyard to the battlefield. If this spell was cast from a graveyard, you may copy this spell and may choose a new target for the copy.
        this.getSpellAbility().addEffect(new SevinnesReclamationEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));

        // Flashback {4}{W}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{4}{W}"), TimingRule.SORCERY));
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

    private static final Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();

    SevinnesReclamationEffect() {
        super(Outcome.Benefit);
        staticText = "Return target permanent card with converted mana cost 3 or less " +
                "from your graveyard to the battlefield. If this spell was cast from a graveyard, " +
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
        Spell spell = (Spell) game.getStack().getStackObject(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (spell == null || player == null) {
            return false;
        }
        effect.apply(game, source);
        if (spell.getFromZone() == Zone.GRAVEYARD
                && player.chooseUse(outcome, "Copy this spell?", source, game)) {
            spell.createCopyOnStack(game, source, source.getControllerId(), true);
        }
        return true;
    }
}

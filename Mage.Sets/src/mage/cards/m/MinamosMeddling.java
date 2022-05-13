package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MinamosMeddling extends CardImpl {

    public MinamosMeddling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Counter target spell. That spell's controller reveals their hand, then discards each card with the same name as a card spliced onto that spell.
        this.getSpellAbility().addEffect(new MinamosMeddlingCounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private MinamosMeddling(final MinamosMeddling card) {
        super(card);
    }

    @Override
    public MinamosMeddling copy() {
        return new MinamosMeddling(this);
    }
}

class MinamosMeddlingCounterTargetEffect extends OneShotEffect {

    MinamosMeddlingCounterTargetEffect() {
        super(Outcome.Benefit);
        staticText = "Counter target spell. That spell's controller reveals their hand, then discards each card with the same name as a card spliced onto that spell";
    }

    private MinamosMeddlingCounterTargetEffect(final MinamosMeddlingCounterTargetEffect effect) {
        super(effect);
    }

    @Override
    public MinamosMeddlingCounterTargetEffect copy() {
        return new MinamosMeddlingCounterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source);
        if (sourceObject == null) {
            return false;
        }
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Spell spell = game.getStack().getSpell(targetId);
            if (spell == null) {
                continue;
            }
            game.getStack().counter(targetId, source, game);
            Player spellController = game.getPlayer(spell.getControllerId());
            if (spellController == null) {
                continue;
            }
            spellController.revealCards(sourceObject.getName(), spellController.getHand(), game);
            Cards cardsToDiscard = new CardsImpl();
            for (SpellAbility spellAbility : spell.getSpellAbilities()) {
                if (spellAbility.getSpellAbilityType() == SpellAbilityType.SPLICE) {
                    for (Card card : spellController.getHand().getCards(game)) {
                        if (card.getName().equals(spellAbility.getCardName())) {
                            cardsToDiscard.add(card);
                        }
                    }
                }
            }
            if (!cardsToDiscard.isEmpty()) {
                spellController.discard(cardsToDiscard, false, source, game);
            }
        }
        return true;
    }
}

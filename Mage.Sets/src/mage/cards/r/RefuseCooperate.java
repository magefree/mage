package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RefuseCooperate extends SplitCard {

    public RefuseCooperate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.INSTANT}, "{3}{R}", "{2}{U}", SpellAbilityType.SPLIT_AFTERMATH);

        // Refuse
        // Refuse deals damage to target spell's controller equal to that spell's converted mana cost.
        getLeftHalfCard().getSpellAbility().addEffect(new RefuseEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetSpell());

        // Cooperate
        // Aftermath
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        // Copy target instant or sorcery spell. You may choose new targets for the copy.
        getRightHalfCard().getSpellAbility().addEffect(new CopyTargetSpellEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetSpell(new FilterInstantOrSorcerySpell()));
    }

    private RefuseCooperate(final RefuseCooperate card) {
        super(card);
    }

    @Override
    public RefuseCooperate copy() {
        return new RefuseCooperate(this);
    }
}

class RefuseEffect extends OneShotEffect {

    public RefuseEffect() {
        super(Outcome.Damage);
        staticText = "Refuse deals damage to target spell's controller equal to that spell's mana value";
    }

    private RefuseEffect(final RefuseEffect effect) {
        super(effect);
    }

    @Override
    public RefuseEffect copy() {
        return new RefuseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
            if (spell != null) {
                Player spellController = game.getPlayer(spell.getControllerId());
                if (spellController != null) {
                    spellController.damage(spell.getManaValue(), source.getSourceId(), source, game);
                    return true;
                }
            }
        }
        return false;
    }
}

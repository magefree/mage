package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetWithReplacementEffect;
import mage.abilities.effects.common.ReturnToLibrarySpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class SpellCrumple extends CardImpl {

    public SpellCrumple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}");

        // Counter target spell. If that spell is countered this way, put it on the
        // bottom of its owner's library instead of into that player's graveyard.
        // Put Spell Crumple on the bottom of its owner's library.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterTargetWithReplacementEffect(PutCards.BOTTOM_ANY));
        this.getSpellAbility().addEffect(new ReturnToLibrarySpellEffect(false));
    }

    private SpellCrumple(final SpellCrumple card) {
        super(card);
    }

    @Override
    public SpellCrumple copy() {
        return new SpellCrumple(this);
    }
}

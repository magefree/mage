package mage.cards.y;

import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YouFindTheVillainsLair extends CardImpl {

    public YouFindTheVillainsLair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Choose one —
        // • Foil Their Scheme — Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().withFirstModeFlavorWord("Foil Their Scheme");

        // • Learn Their Secrets — Draw two cards, then discard two cards.
        this.getSpellAbility().addMode(new Mode(
                new DrawDiscardControllerEffect(2, 2)
        ).withFlavorWord("Learn Their Secrets"));
    }

    private YouFindTheVillainsLair(final YouFindTheVillainsLair card) {
        super(card);
    }

    @Override
    public YouFindTheVillainsLair copy() {
        return new YouFindTheVillainsLair(this);
    }
}

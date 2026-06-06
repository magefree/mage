package mage.cards.l;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BlackWizardToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LindblumIndustrialRegency extends AdventureCard {

    public LindblumIndustrialRegency(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.LAND}, new SubType[]{SubType.TOWN}, "",
                "Mage Siege",
                new CardType[]{CardType.INSTANT}, "{2}{R}");

        // This land enters tapped.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R}.
        this.getLeftHalfCard().addAbility(new RedManaAbility());

        // Mage Siege
        // Create a 0/1 black Wizard creature token with "Whenever you cast a noncreature spell, this token deals 1 damage to each opponent."
        this.getRightHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(new BlackWizardToken()));

        finalizeCard();
    }

    private LindblumIndustrialRegency(final LindblumIndustrialRegency card) {
        super(card);
    }

    @Override
    public LindblumIndustrialRegency copy() {
        return new LindblumIndustrialRegency(this);
    }
}

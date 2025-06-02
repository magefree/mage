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
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, new CardType[]{CardType.INSTANT}, "", "Mage Siege", "{2}{R}");

        this.subtype.add(SubType.TOWN);

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // Mage Siege
        // Create a 0/1 black Wizard creature token with "Whenever you cast a noncreature spell, this token deals 1 damage to each opponent."
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new BlackWizardToken()));
        this.finalizeAdventure();
    }

    private LindblumIndustrialRegency(final LindblumIndustrialRegency card) {
        super(card);
    }

    @Override
    public LindblumIndustrialRegency copy() {
        return new LindblumIndustrialRegency(this);
    }
}

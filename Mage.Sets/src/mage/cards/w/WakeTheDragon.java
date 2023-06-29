package mage.cards.w;

import java.util.UUID;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.DragonMenaceAndStealArtifactToken;

/**
 * @author rullinoiz
 */
public final class WakeTheDragon extends CardImpl {

    public WakeTheDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{R}");
        

        // Create a 6/6 black and red Dragon creature token with flying, menace, and "Whenever this creature deals combat damage to a player, gain control of target artifact that player controls."
        this.getSpellAbility().addEffect(new CreateTokenEffect(new DragonMenaceAndStealArtifactToken()));

        // Flashback {6}{B}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{6}{B}{R}")));
    }

    private WakeTheDragon(final WakeTheDragon card) {
        super(card);
    }

    @Override
    public WakeTheDragon copy() {
        return new WakeTheDragon(this);
    }
}
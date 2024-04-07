package mage.game.command.emblems;

import mage.abilities.effects.keyword.RadCounterTriggeredAbility;
import mage.cards.repository.TokenInfo;
import mage.cards.repository.TokenRepository;
import mage.game.command.Emblem;

/**
 * Special emblem to enable the Rad Counter inherent trigger
 * with an actual source, to display image on the stack.
 *
 * @author Susucr
 */
public class RadiationEmblem extends Emblem {

    public RadiationEmblem() {
        super("Radiation");
        this.getAbilities().add(new RadCounterTriggeredAbility());

        TokenInfo foundInfo = TokenRepository.instance.findPreferredTokenInfoForXmage(TokenRepository.XMAGE_IMAGE_NAME_RADIATION, null);
        if (foundInfo != null) {
            this.setExpansionSetCode(foundInfo.getSetCode());
            this.setCardNumber("");
            this.setImageFileName(""); // use default
            this.setImageNumber(foundInfo.getImageNumber());
        } else {
            // how-to fix: add emblem to the tokens-database
            throw new IllegalArgumentException("Wrong code usage: can't find xmage token info for: " + TokenRepository.XMAGE_IMAGE_NAME_RADIATION);
        }
    }

    private RadiationEmblem(final RadiationEmblem card) {
        super(card);
    }

    @Override
    public RadiationEmblem copy() {
        return new RadiationEmblem(this);
    }
}
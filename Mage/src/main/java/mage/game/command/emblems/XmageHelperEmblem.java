package mage.game.command.emblems;

import mage.MageObject;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.hint.Hint;
import mage.cards.FrameStyle;
import mage.cards.repository.TokenInfo;
import mage.cards.repository.TokenRepository;
import mage.constants.Zone;
import mage.game.command.Emblem;

/**
 * GUI: inner xmage emblem to show additional info for players like global hints
 *
 * @author JayDi85
 */
public class XmageHelperEmblem extends Emblem {

    public XmageHelperEmblem() {
        super("Helper Emblem");
        this.frameStyle = FrameStyle.M15_NORMAL;

        // helper don't have source, so image can be initialized immediately
        setSourceObjectAndInitImage(null);
    }

    @Override
    public void setSourceObjectAndInitImage(MageObject sourceObject) {
        this.sourceObject = sourceObject;
        TokenInfo foundInfo = TokenRepository.instance.findPreferredTokenInfoForXmage(TokenRepository.XMAGE_IMAGE_NAME_HELPER_EMBLEM, null);
        if (foundInfo != null) {
            this.setExpansionSetCode(foundInfo.getSetCode());
            this.setUsesVariousArt(false);
            this.setCardNumber("");
            this.setImageFileName(""); // use default
            this.setImageNumber(foundInfo.getImageNumber());
        } else {
            // how-to fix: add image to the tokens-database TokenRepository->loadXmageTokens
            throw new IllegalArgumentException("Wrong code usage: can't find xmage token info for: " + TokenRepository.XMAGE_IMAGE_NAME_HELPER_EMBLEM);
        }
    }

    private XmageHelperEmblem(final XmageHelperEmblem card) {
        super(card);
    }

    @Override
    public XmageHelperEmblem copy() {
        return new XmageHelperEmblem(this);
    }

    public XmageHelperEmblem withCardHint(String name, Hint hint) {
        this.getAbilities().add(new SimpleStaticAbility(
                Zone.ALL,
                new InfoEffect(name)).addHint(hint)
        );
        return this;
    }
}
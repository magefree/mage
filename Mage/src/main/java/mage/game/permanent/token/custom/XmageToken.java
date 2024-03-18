package mage.game.permanent.token.custom;

import mage.abilities.Ability;
import mage.cards.FrameStyle;
import mage.game.permanent.token.TokenImpl;

/**
 * GUI: inner xmage token object to show custom images (information tokens like morph, blessing, etc)
 *
 * @author JayDi85
 */
public final class XmageToken extends TokenImpl {

    public XmageToken(String name) {
        super(name, "");
        this.frameStyle = FrameStyle.BFZ_FULL_ART_BASIC; // use full art for better visual in card viewer
    }

    public XmageToken withAbility(Ability ability) {
        this.addAbility(ability);
        return this;
    }

    private XmageToken(final XmageToken token) {
        super(token);
    }

    public XmageToken copy() {
        return new XmageToken(this);
    }
}

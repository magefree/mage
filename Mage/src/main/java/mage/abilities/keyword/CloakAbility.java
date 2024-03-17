package mage.abilities.keyword;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenAttachSourceEffect;
import mage.game.permanent.token.PhyrexianGermToken;

public class CloakAbility extends EntersBattlefieldTriggeredAbility {
    public CloakAbility() {
        super(new CreateTokenAttachSourceEffect(new PhyrexianGermToken()));
    }

    protected CloakAbility(final CloakAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "When Cryptic Coat enters the battlefield, cloak the top card of your library, then attach Cryptic Coat to it." +
                "<i>(To cloak a card, put it onto the battlefield face down as a 2/2 creature with ward {2}. Turn it face up any time for its mana cost if it’s a creature card.)</i> \n" +
                "Equipped creature gets +1/+0 and can’t be blocked. \n" +
                "1{U}: Return Cryptic Coat to its owner’s hand.";
    }

    @Override
    public CloakAbility copy() {
        return new CloakAbility(this);
    }
}

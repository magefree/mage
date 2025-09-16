package mage.cards.o;

import mage.abilities.common.EntersBattlefieldFromGraveyardTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.MayhemLandAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class OscorpIndustries extends CardImpl {

    public OscorpIndustries(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When this land enters from a graveyard, you lose 2 life.
        this.addAbility(new EntersBattlefieldFromGraveyardTriggeredAbility(new LoseLifeSourceControllerEffect(2)));

        // {T}: Add {U}, {B}, or {R}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());

        // Mayhem
        this.addAbility(new MayhemLandAbility(this));

    }

    private OscorpIndustries(final OscorpIndustries card) {
        super(card);
    }

    @Override
    public OscorpIndustries copy() {
        return new OscorpIndustries(this);
    }
}

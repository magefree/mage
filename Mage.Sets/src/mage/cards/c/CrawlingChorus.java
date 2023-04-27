package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.PhyrexianMiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrawlingChorus extends CardImpl {

    public CrawlingChorus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Toxic 1
        this.addAbility(new ToxicAbility(1));

        // When Crawling Chorus dies, create a 1/1 colorless Phyreixan Mite artifact creature token with toxic 1 and "This creature can't block."
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new PhyrexianMiteToken())));
    }

    private CrawlingChorus(final CrawlingChorus card) {
        super(card);
    }

    @Override
    public CrawlingChorus copy() {
        return new CrawlingChorus(this);
    }
}

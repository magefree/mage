package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.DetectiveToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MuseumNightwatch extends CardImpl {

    public MuseumNightwatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Museum Nightwatch dies, create a 2/2 white and blue Detective creature token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new DetectiveToken())));

        // Disguise {1}{W}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{1}{W}")));
    }

    private MuseumNightwatch(final MuseumNightwatch card) {
        super(card);
    }

    @Override
    public MuseumNightwatch copy() {
        return new MuseumNightwatch(this);
    }
}

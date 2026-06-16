package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.ApeVillainToken;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RedGhostIntangibleGenius extends CardImpl {

    public RedGhostIntangibleGenius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Red Ghost can't be blocked.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedSourceEffect()));

        // Whenever you draw your second card each turn, create a 3/3 red Ape Villain creature token with haste.
        this.addAbility(new DrawNthCardTriggeredAbility(new CreateTokenEffect(new ApeVillainToken())));
    }

    private RedGhostIntangibleGenius(final RedGhostIntangibleGenius card) {
        super(card);
    }

    @Override
    public RedGhostIntangibleGenius copy() {
        return new RedGhostIntangibleGenius(this);
    }
}

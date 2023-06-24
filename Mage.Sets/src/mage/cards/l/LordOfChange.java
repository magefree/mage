package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LordOfChange extends CardImpl {

    public LordOfChange(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {3}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{3}"), false));

        // Architect of Deception -- When Lord of Change enters the battlefield, draw three cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(3)).withFlavorWord("Architect of Deception"));
    }

    private LordOfChange(final LordOfChange card) {
        super(card);
    }

    @Override
    public LordOfChange copy() {
        return new LordOfChange(this);
    }
}

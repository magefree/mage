package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

public class FeralProwler extends CardImpl {

    public FeralProwler(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        subtype.add("Cat");

        power = new MageInt(1);
        toughness = new MageInt(3);

        addAbility(new DiesTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
    }

    public FeralProwler(final FeralProwler feralProwler) {
        super(feralProwler);
    }

    public FeralProwler copy() {
        return new FeralProwler(this);
    }
}

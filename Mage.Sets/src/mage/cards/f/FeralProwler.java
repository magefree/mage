package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

public final class FeralProwler extends CardImpl {

    public FeralProwler(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        subtype.add(SubType.CAT);

        power = new MageInt(1);
        toughness = new MageInt(3);

        addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
    }

    private FeralProwler(final FeralProwler feralProwler) {
        super(feralProwler);
    }

    public FeralProwler copy() {
        return new FeralProwler(this);
    }
}

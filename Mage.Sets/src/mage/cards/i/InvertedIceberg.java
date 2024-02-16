package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.CraftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvertedIceberg extends CardImpl {

    public InvertedIceberg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");
        this.secondSideCardClazz = mage.cards.i.IcebergTitan.class;

        // When Inverted Iceberg enters the battlefield, mill a card, then draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(1));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        this.addAbility(ability);

        // Craft with artifact {4}{U}{U}
        this.addAbility(new CraftAbility("{4}{U}{U}"));
    }

    private InvertedIceberg(final InvertedIceberg card) {
        super(card);
    }

    @Override
    public InvertedIceberg copy() {
        return new InvertedIceberg(this);
    }
}

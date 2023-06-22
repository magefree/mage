package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.BecomesTargetOpponentAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BattleMammoth extends CardImpl {

    public BattleMammoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a permanent you control becomes the target of a spell or ability an opponent controls, you may draw a card.
        this.addAbility(new BecomesTargetOpponentAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1), true
        ));

        // Foretell {2}{G}{G}
        this.addAbility(new ForetellAbility(this, "{2}{G}{G}"));
    }

    private BattleMammoth(final BattleMammoth card) {
        super(card);
    }

    @Override
    public BattleMammoth copy() {
        return new BattleMammoth(this);
    }
}

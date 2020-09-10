package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OverwhelmedApprentice extends CardImpl {

    public OverwhelmedApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Overwhelmed Apprentice enters the battlefield, each opponent puts the top two cards of their library into their graveyard. Then you scry 2.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new MillCardsEachPlayerEffect(2, TargetController.OPPONENT)
        );
        ability.addEffect(new ScryEffect(2, false).setText("Then you scry 2."));
        this.addAbility(ability);
    }

    private OverwhelmedApprentice(final OverwhelmedApprentice card) {
        super(card);
    }

    @Override
    public OverwhelmedApprentice copy() {
        return new OverwhelmedApprentice(this);
    }
}

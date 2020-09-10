package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MindwrackHarpy extends CardImpl {

    public MindwrackHarpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HARPY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of combat on your turn, each player puts the top three cards of their library into their graveyard.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new MillCardsEachPlayerEffect(3, TargetController.ANY),
                TargetController.YOU, false
        ));
    }

    private MindwrackHarpy(final MindwrackHarpy card) {
        super(card);
    }

    @Override
    public MindwrackHarpy copy() {
        return new MindwrackHarpy(this);
    }
}

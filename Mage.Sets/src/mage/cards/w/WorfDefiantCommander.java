package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.AttackedThisTurnOpponentsCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.KlingonWarriorToken;
import mage.watchers.common.PlayersAttackedThisTurnWatcher;

import java.util.UUID;

/**
 * @author muz
 */
public final class WorfDefiantCommander extends CardImpl {

    public WorfDefiantCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Worf enters, create a 2/1 red Klingon Warrior creature token with haste.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KlingonWarriorToken())));

        // Battlefront -- Whenever you attack, exile the top X cards of your library, where X is the number of opponents you attacked this turn. Until the end of your next turn, you may play those cards.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
            new ExileTopXMayPlayUntilEffect(
                AttackedThisTurnOpponentsCount.instance, false, Duration.UntilEndOfYourNextTurn
            ).setText("exile the top X cards of your library, where X is the number of opponents you attacked this turn. "
                + "Until the end of your next turn, you may play those cards"), 1
        ).withFlavorWord("Battlefront").addHint(AttackedThisTurnOpponentsCount.getHint()),
            new PlayersAttackedThisTurnWatcher());
    }

    private WorfDefiantCommander(final WorfDefiantCommander card) {
        super(card);
    }

    @Override
    public WorfDefiantCommander copy() {
        return new WorfDefiantCommander(this);
    }
}

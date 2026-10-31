package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.dynamicvalue.common.AttackedThisTurnOpponentsCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.KlingonWarriorToken;
import mage.watchers.common.PlayersAttackedThisTurnWatcher;

/**
 * @author muz
 */
public final class RiteOfAscension extends CardImpl {

    public RiteOfAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{W}");


        // Battlefront -- Whenever you attack, for each opponent you attacked this turn, create a tapped and attacking 2/1 red Klingon Warrior creature token with haste.
        this.addAbility(
            new AttacksWithCreaturesTriggeredAbility(
                new CreateTokenEffect(
                    new KlingonWarriorToken(),
                    AttackedThisTurnOpponentsCount.instance, true, true
                ).setText("for each opponent you attacked this turn, create a tapped and attacking "
                    + "2/1 red Klingon Warrior creature token with haste"),
                1
            ).withFlavorWord("Battlefront")
            .addHint(AttackedThisTurnOpponentsCount.getHint()),
            new PlayersAttackedThisTurnWatcher());
    }

    private RiteOfAscension(final RiteOfAscension card) {
        super(card);
    }

    @Override
    public RiteOfAscension copy() {
        return new RiteOfAscension(this);
    }
}

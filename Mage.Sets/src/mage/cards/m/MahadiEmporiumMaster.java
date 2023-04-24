package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.common.CreaturesDiedThisTurnCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.CreaturesDiedThisTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.permanent.token.TreasureToken;
import mage.watchers.common.CreaturesDiedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MahadiEmporiumMaster extends CardImpl {

    public MahadiEmporiumMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, create a Treasure token for each creature that died this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(
                        new TreasureToken(), CreaturesDiedThisTurnCount.instance
                ).setText("create a Treasure token for each creature that died this turn"), TargetController.YOU, false
        ).addHint(CreaturesDiedThisTurnHint.instance));
    }

    private MahadiEmporiumMaster(final MahadiEmporiumMaster card) {
        super(card);
    }

    @Override
    public MahadiEmporiumMaster copy() {
        return new MahadiEmporiumMaster(this);
    }
}

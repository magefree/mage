package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.HaventCastSpellFromHandThisTurnCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.permanent.token.Spirit22Token;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class WranglerOfTheDamned extends CardImpl {

    public WranglerOfTheDamned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // At the beginning of your end step, if you haven't cast a spell from your hand this turn, create a 2/2 white Spirit creature token with flying.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new Spirit22Token()),
                TargetController.YOU, HaventCastSpellFromHandThisTurnCondition.instance, false
        ).addHint(HaventCastSpellFromHandThisTurnCondition.hint));
    }

    private WranglerOfTheDamned(final WranglerOfTheDamned card) {
        super(card);
    }

    @Override
    public WranglerOfTheDamned copy() {
        return new WranglerOfTheDamned(this);
    }
}

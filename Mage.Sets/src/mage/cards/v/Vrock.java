package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Vrock extends CardImpl {

    public Vrock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Toxic Spores — At the beginning of your end step, if a permanent you controlled left the battlefield this turn, each opponent loses 3 life.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new LoseLifeOpponentsEffect(3), TargetController.YOU,
                RevoltCondition.instance, false
        ).withFlavorWord("Toxic Spores"), new RevoltWatcher());
    }

    private Vrock(final Vrock card) {
        super(card);
    }

    @Override
    public Vrock copy() {
        return new Vrock(this);
    }
}

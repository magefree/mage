package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class RaffinesSilencer extends CardImpl {

    public RaffinesSilencer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Raffine's Silencer enters the battlefield, it connives.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConniveSourceEffect()));

        // When Raffine's Silencer dies, target creature an opponent controls gets -X/-X until end of turn, where X is Raffine's Silencer's power.
        Ability ability = new DiesSourceTriggeredAbility(
                new BoostTargetEffect(RaffinesSilencerValue.instance, RaffinesSilencerValue.instance, Duration.EndOfTurn),
                false
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private RaffinesSilencer(final RaffinesSilencer card) {
        super(card);
    }

    @Override
    public RaffinesSilencer copy() {
        return new RaffinesSilencer(this);
    }
}

enum RaffinesSilencerValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Object died = effect.getValue("permanentLeftBattlefield");
        if (died instanceof Permanent) {
            return -((Permanent) died).getPower().getValue();
        }
        return 0;
    }

    @Override
    public RaffinesSilencerValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "-X";
    }

    @Override
    public String getMessage() {
        return "{this}'s power";
    }

    @Override
    public int getSign() {
        return -1;
    }
}

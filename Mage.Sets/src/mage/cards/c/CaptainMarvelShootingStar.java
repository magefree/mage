package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.TargetPermanentPowerCount;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.common.ExileAndGainLifeEqualPowerTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.targetpointer.FixedTarget;

/**
 * @author muz
 */
public final class CaptainMarvelShootingStar extends CardImpl {

    public CaptainMarvelShootingStar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KREE);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Captain Marvel enters or attacks, exile up to one target creature. That creature's controller gains life equal to its power.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(
            new ExileAndGainLifeEqualPowerTargetEffect()
                .setText("exile target creature. That creature's controller gains life equal to its power")
        );
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Whenever a creature other than Captain Marvel is exiled from the battlefield, you gain life equal to its power.
        this.addAbility(new CaptainMarvelShootingStarTriggeredAbility());
    }

    private CaptainMarvelShootingStar(final CaptainMarvelShootingStar card) {
        super(card);
    }

    @Override
    public CaptainMarvelShootingStar copy() {
        return new CaptainMarvelShootingStar(this);
    }
}

class CaptainMarvelShootingStarTriggeredAbility extends ZoneChangeTriggeredAbility {

    CaptainMarvelShootingStarTriggeredAbility() {
        super(
            Zone.BATTLEFIELD,
            Zone.BATTLEFIELD, Zone.EXILED,
            new GainLifeEffect(TargetPermanentPowerCount.instance),
            "Whenever a creature other than Captain Marvel is exiled from the battlefield, ", false
        );
    }

    private CaptainMarvelShootingStarTriggeredAbility(final CaptainMarvelShootingStarTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CaptainMarvelShootingStarTriggeredAbility copy() {
        return new CaptainMarvelShootingStarTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent != null && permanent.isCreature(game) && permanent.getId() != this.getSourceId() ) {
            // custom check cause ZoneChangeTriggeredAbility for source object only
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if ((fromZone == null || zEvent.getFromZone() == fromZone)
                && (zEvent.getToZone() == toZone || zEvent.getOriginalToZone() == toZone)) {
                getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
                return true;
            }
        }
        return false;
    }
}


package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class EfreetWeaponmaster extends CardImpl {

    public EfreetWeaponmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{R}{W}");
        this.subtype.add(SubType.EFREET);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        
        // When Efreet Weaponmaster enters the battlefield or is turned face up, another target creature you control gets +3/+0 until end of turn.
        this.addAbility(new EfreetWeaponmasterAbility());

        // Morph {2}{U}{R}{W}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{2}{U}{R}{W}")));
    }

    private EfreetWeaponmaster(final EfreetWeaponmaster card) {
        super(card);
    }

    @Override
    public EfreetWeaponmaster copy() {
        return new EfreetWeaponmaster(this);
    }
}

class EfreetWeaponmasterAbility extends TriggeredAbilityImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public EfreetWeaponmasterAbility() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(3,0, Duration.EndOfTurn), false);
        this.addTarget(new TargetControlledCreaturePermanent(filter));
        this.setWorksFaceDown(true);
        setTriggerPhrase("When {this} enters the battlefield or is turned face up, ");
    }

    public EfreetWeaponmasterAbility(final EfreetWeaponmasterAbility ability) {
        super(ability);
    }

    @Override
    public EfreetWeaponmasterAbility copy() {
        return new EfreetWeaponmasterAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TURNEDFACEUP || event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TURNEDFACEUP && event.getTargetId().equals(this.getSourceId())) {
            return true;
        }
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD && event.getTargetId().equals(this.getSourceId()) ) {
            Permanent sourcePermanent = game.getPermanent(getSourceId());
            if (sourcePermanent != null && !sourcePermanent.isFaceDown(game)) {
                return true;
            }
        }
        return false;
    }
}

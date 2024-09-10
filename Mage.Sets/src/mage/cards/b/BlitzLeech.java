package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.RemoveAllCountersPermanentTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlitzLeech extends CardImpl {

    public BlitzLeech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");

        this.subtype.add(SubType.LEECH);
        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Blitz Leech enters the battlefield, target creature an opponent controls gets -2/-2 until end of turn. Remove all counters from that creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-2, -2));
        ability.addEffect(new RemoveAllCountersPermanentTargetEffect().setText("Remove all counters from that creature"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private BlitzLeech(final BlitzLeech card) {
        super(card);
    }

    @Override
    public BlitzLeech copy() {
        return new BlitzLeech(this);
    }
}
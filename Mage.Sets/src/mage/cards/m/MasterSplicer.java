
package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.GolemToken;

import java.util.UUID;

/**
 * @author North, Loki
 */
public final class MasterSplicer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.GOLEM, "Golems");

    public MasterSplicer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GolemToken(expansionSetCode))));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));
    }

    public MasterSplicer(final MasterSplicer card) {
        super(card);
    }

    @Override
    public MasterSplicer copy() {
        return new MasterSplicer(this);
    }
}

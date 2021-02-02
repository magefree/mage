package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class Petravark extends CardImpl {

    public Petravark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Petravark enters the battlefield, exile target land.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetForSourceEffect(), false);
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);

        // When Petravark leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false));
    }

    private Petravark(final Petravark card) {
        super(card);
    }

    @Override
    public Petravark copy() {
        return new Petravark(this);
    }
}

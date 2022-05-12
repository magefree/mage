package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class GiantAmbushBeetle extends CardImpl {

    public GiantAmbushBeetle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B/G}{R}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Giant Ambush Beetle enters the battlefield, you may have target creature block it this turn if able.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new MustBeBlockedByTargetSourceEffect(Duration.EndOfTurn)
                        .setText("target creature block it this turn if able"), true
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GiantAmbushBeetle(final GiantAmbushBeetle card) {
        super(card);
    }

    @Override
    public GiantAmbushBeetle copy() {
        return new GiantAmbushBeetle(this);
    }
}

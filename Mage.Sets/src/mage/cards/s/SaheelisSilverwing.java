package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryTopCardTargetPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SaheelisSilverwing extends CardImpl {

    public SaheelisSilverwing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Saheeli's Silverwing enters the battlefield, look at the top card of target opponent's library.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new LookLibraryTopCardTargetPlayerEffect().setText("look at the top card of target opponent's library")
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private SaheelisSilverwing(final SaheelisSilverwing card) {
        super(card);
    }

    @Override
    public SaheelisSilverwing copy() {
        return new SaheelisSilverwing(this);
    }
}

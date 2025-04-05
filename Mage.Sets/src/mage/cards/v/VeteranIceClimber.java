package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VeteranIceClimber extends CardImpl {

    public VeteranIceClimber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // This creature can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // Whenever this creature attacks, up to one target player mills cards equal to this creature's power.
        Ability ability = new AttacksTriggeredAbility(new MillCardsTargetEffect(SourcePermanentPowerValue.NOT_NEGATIVE));
        ability.addTarget(new TargetPlayer(0, 1, false));
        this.addAbility(ability);
    }

    private VeteranIceClimber(final VeteranIceClimber card) {
        super(card);
    }

    @Override
    public VeteranIceClimber copy() {
        return new VeteranIceClimber(this);
    }
}

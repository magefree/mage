package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThistledownPlayers extends CardImpl {

    public ThistledownPlayers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.MOUSE);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Thistledown Players attacks, untap target nonland permanent.
        Ability ability = new AttacksTriggeredAbility(new UntapTargetEffect());
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);
    }

    private ThistledownPlayers(final ThistledownPlayers card) {
        super(card);
    }

    @Override
    public ThistledownPlayers copy() {
        return new ThistledownPlayers(this);
    }
}

package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GraspingGiant extends CardImpl {

    public GraspingGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Grasping Giant becomes blocked by a creature, exile that creature until Grasping Giant leaves the battlefield.
        Ability ability = new BecomesBlockedByCreatureTriggeredAbility(new ExileUntilSourceLeavesEffect(), false);
        this.addAbility(ability);
    }

    private GraspingGiant(final GraspingGiant card) {
        super(card);
    }

    @Override
    public GraspingGiant copy() {
        return new GraspingGiant(this);
    }
}

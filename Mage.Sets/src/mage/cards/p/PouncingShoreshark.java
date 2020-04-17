package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.MutateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PouncingShoreshark extends CardImpl {

    public PouncingShoreshark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.SHARK);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Mutate {3}{U}
        this.addAbility(new MutateAbility(this, "{3}{U}"));

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When this creature mutates, you may return target creature an opponent controls to its owner's hand.
        Ability ability = new MutatesSourceTriggeredAbility(new ReturnToHandTargetEffect(), true);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private PouncingShoreshark(final PouncingShoreshark card) {
        super(card);
    }

    @Override
    public PouncingShoreshark copy() {
        return new PouncingShoreshark(this);
    }
}

package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.AttachTargetToSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ArmoryAutomaton extends CardImpl {

    public ArmoryAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Armory Automaton enters or attacks, you may attach any number of target Equipment to it.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new AttachTargetToSourceEffect().setText("attach any number of target Equipment to it"), true);
        ability.addTarget(new TargetPermanent(0, Integer.MAX_VALUE, StaticFilters.FILTER_PERMANENT_EQUIPMENT));
        this.addAbility(ability);
    }

    private ArmoryAutomaton(final ArmoryAutomaton card) {
        super(card);
    }

    @Override
    public ArmoryAutomaton copy() {
        return new ArmoryAutomaton(this);
    }
}

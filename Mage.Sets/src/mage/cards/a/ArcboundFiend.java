package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.counter.MoveCountersFromTargetToSourceEffect;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.ModularAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class ArcboundFiend extends CardImpl {

    public ArcboundFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Fear
        this.addAbility(FearAbility.getInstance());

        // At the beginning of your upkeep, you may move a +1/+1 counter from target creature onto Arcbound Fiend.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new MoveCountersFromTargetToSourceEffect(), TargetController.YOU, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Modular 3
        this.addAbility(new ModularAbility(this, 3));
    }

    private ArcboundFiend(final ArcboundFiend card) {
        super(card);
    }

    @Override
    public ArcboundFiend copy() {
        return new ArcboundFiend(this);
    }
}

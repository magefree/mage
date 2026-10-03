package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.SubType;
import mage.game.permanent.token.HeartwoodToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.counters.CounterType;

/**
 *
 * @author muz
 */
public final class HungeringPuppetbeast extends CardImpl {

    private static final FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent("another artifact");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public HungeringPuppetbeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When this creature enters, create a Heartwood token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new HeartwoodToken())));

        // {1}, Sacrifice another artifact: Put a +1/+1 counter on this creature. It gains your choice of trample, hexproof, or haste until end of turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeTargetCost(filter));
        ability.addEffect(new GainsChoiceOfAbilitiesEffect(
            GainsChoiceOfAbilitiesEffect.TargetType.Source,
            TrampleAbility.getInstance(), HexproofAbility.getInstance(), HasteAbility.getInstance()
        ).setText("It gains your choice of trample, hexproof, or haste until end of turn"));
        this.addAbility(ability);
    }

    private HungeringPuppetbeast(final HungeringPuppetbeast card) {
        super(card);
    }

    @Override
    public HungeringPuppetbeast copy() {
        return new HungeringPuppetbeast(this);
    }
}

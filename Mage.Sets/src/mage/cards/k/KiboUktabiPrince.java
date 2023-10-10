package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenAllEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.BananaToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KiboUktabiPrince extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature you control that's an Ape or a Monkey");
    private static final FilterPermanent filter2
            = new FilterArtifactPermanent("an artifact an opponent controls");

    static {
        filter.add(Predicates.or(
                SubType.APE.getPredicate(),
                SubType.MONKEY.getPredicate()
        ));
        filter2.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public KiboUktabiPrince(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MONKEY);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Each player creates a colorless artifact token named Banana with "{T}, Sacrifice this artifact: Add {R} or {G}. You gain 2 life."
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenAllEffect(new BananaToken(), TargetController.EACH_PLAYER), new TapSourceCost()
        ));

        // Whenever an artifact an opponent controls is put into a graveyard from the battlefield, put a +1/+1 counter on each creature you control that's an Ape or Monkey.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter),
                false, filter2, false
        ));

        // Whenever Kibo attacks, defending player sacrifices an artifact.
        this.addAbility(new AttacksTriggeredAbility(new SacrificeEffect(
                StaticFilters.FILTER_PERMANENT_ARTIFACT_AN, 1, "defending player"
        ), false, null, SetTargetPointer.PLAYER));
    }

    private KiboUktabiPrince(final KiboUktabiPrince card) {
        super(card);
    }

    @Override
    public KiboUktabiPrince copy() {
        return new KiboUktabiPrince(this);
    }
}

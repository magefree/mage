package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenControllerTargetPermanentEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.token.OxToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class TransmogrifyingWand extends CardImpl {

    public TransmogrifyingWand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Transmogrifying Wand enters the battlefield with three charge counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance(3)),
                "with three charge counters on it"
        ));

        // {1}, {T}, Remove a charge counter from Transmogrifying Wand: Destroy target creature. Its controller creates a 2/4 white Ox creature token. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD,
                new DestroyTargetEffect(),
                new GenericManaCost(1)
        );
        ability.addEffect(new CreateTokenControllerTargetPermanentEffect(new OxToken()));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TransmogrifyingWand(final TransmogrifyingWand card) {
        super(card);
    }

    @Override
    public TransmogrifyingWand copy() {
        return new TransmogrifyingWand(this);
    }
}
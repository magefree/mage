package mage.cards.v;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VincentVengefulAtoner extends CardImpl {

    public VincentVengefulAtoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever one or more creatures you control deal combat damage to a player, put a +1/+1 counter on Vincent.
        this.addAbility(new OneOrMoreCombatDamagePlayerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ));

        // Chaos -- Whenever Vincent deals combat damage to an opponent, it deals that much damage to each other opponent if Vincent's power is 7 or greater.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(
                new VincentVengefulAtonerEffect(), false, true, true
        ).withFlavorWord("Chaos"));
    }

    private VincentVengefulAtoner(final VincentVengefulAtoner card) {
        super(card);
    }

    @Override
    public VincentVengefulAtoner copy() {
        return new VincentVengefulAtoner(this);
    }
}

class VincentVengefulAtonerEffect extends OneShotEffect {

    VincentVengefulAtonerEffect() {
        super(Outcome.Benefit);
        staticText = "it deals that much damage to each other opponent if {this}'s power is 7 or greater";
    }

    private VincentVengefulAtonerEffect(final VincentVengefulAtonerEffect effect) {
        super(effect);
    }

    @Override
    public VincentVengefulAtonerEffect copy() {
        return new VincentVengefulAtonerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .orElse(0) < 7) {
            return false;
        }
        int amount = (Integer) getValue("damage");
        if (amount < 1) {
            return false;
        }
        UUID targetId = getTargetPointer().getFirst(game, source);
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Optional.ofNullable(opponentId)
                    .filter(uuid -> !uuid.equals(targetId))
                    .map(game::getPlayer)
                    .map(player -> player.damage(amount, source, game));
        }
        return true;
    }
}

package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.PutCounterOnCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.permanent.token.DeathtouchSnakeToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class HapatraVizierOfPoisons extends CardImpl {

    public HapatraVizierOfPoisons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Hapatra, Vizier of Poisons deals combat damage to a player, you may put a -1/-1 counter on target creature.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance()), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Whenever you put one or more -1/-1 counters on a creature, create a 1/1 green Snake creature token with deathtouch.
        this.addAbility(new PutCounterOnCreatureTriggeredAbility(new CreateTokenEffect(new DeathtouchSnakeToken()), CounterType.M1M1.createInstance()));
    }

    private HapatraVizierOfPoisons(final HapatraVizierOfPoisons card) {
        super(card);
    }

    @Override
    public HapatraVizierOfPoisons copy() {
        return new HapatraVizierOfPoisons(this);
    }
}

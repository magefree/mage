package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TovolarTheMidnightScourge extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("a Wolf or Werewolf you control");

    static {
        filter.add(Predicates.or(
                SubType.WOLF.getPredicate(),
                SubType.WEREWOLF.getPredicate()
        ));
    }

    public TovolarTheMidnightScourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setRed(true);
        this.color.setGreen(true);
        this.nightCard = true;

        // Whenever a Wolf or Werewolf you control deals combat damage to a player, draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter,
                false, SetTargetPointer.NONE, true
        ));

        // {X}{R}{G}: Target Wolf or Werewolf you control gets +X/+0 and gains trample until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("Target Wolf or Werewolf you control gets +X/+0"), new ManaCostsImpl<>("{X}{R}{G}"));
        ability.addEffect(new BoostTargetEffect(
                ManacostVariableValue.REGULAR, StaticValue.get(0), Duration.EndOfTurn
        ).setText("and gains trample until end of turn"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private TovolarTheMidnightScourge(final TovolarTheMidnightScourge card) {
        super(card);
    }

    @Override
    public TovolarTheMidnightScourge copy() {
        return new TovolarTheMidnightScourge(this);
    }
}

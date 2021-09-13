package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.WolfToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TovolarsPackleader extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("another Wolf or Werewolf you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                SubType.WOLF.getPredicate(),
                SubType.WEREWOLF.getPredicate()
        ));
    }

    public TovolarsPackleader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);
        this.color.setGreen(true);
        this.nightCard = true;

        // Whenever Tovolar's Packleader enters the battlefield or attacks, create two 2/2 green Wolf creature tokens.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new CreateTokenEffect(new WolfToken(), 2)
        ));

        // {2}{G}{G}: Another target Wolf or Werewolf you control fights target creature you don't control.
        Ability ability = new SimpleActivatedAbility(new FightTargetsEffect(
                "another target Wolf or Werewolf you control fights target creature you don't control"
        ), new ManaCostsImpl<>("{2}{G}{G}"));
        ability.addTarget(new TargetPermanent(filter));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private TovolarsPackleader(final TovolarsPackleader card) {
        super(card);
    }

    @Override
    public TovolarsPackleader copy() {
        return new TovolarsPackleader(this);
    }
}

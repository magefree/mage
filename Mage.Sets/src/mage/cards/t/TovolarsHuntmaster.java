package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
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
public final class TovolarsHuntmaster extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("another Wolf or Werewolf you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                SubType.WOLF.getPredicate(),
                SubType.WEREWOLF.getPredicate()
        ));
    }

    public TovolarsHuntmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{4}{G}{G}",
                "Tovolar's Packleader",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G"
        );

        // Tovolar's Huntmaster
        this.getLeftHalfCard().setPT(6, 6);

        // Whenever Tovolar's Huntmaster enters the battlefield, create two 2/2 green Wolf creature tokens.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new WolfToken(), 2)));

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Tovolar's Packleader
        this.getRightHalfCard().setPT(7, 7);

        // Whenever Tovolar's Packleader enters the battlefield or attacks, create two 2/2 green Wolf creature tokens.
        this.getRightHalfCard().addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new CreateTokenEffect(new WolfToken(), 2)
        ));

        // {2}{G}{G}: Another target Wolf or Werewolf you control fights target creature you don't control.
        Ability ability = new SimpleActivatedAbility(new FightTargetsEffect().setText(
                "another target Wolf or Werewolf you control fights target creature you don't control"
        ), new ManaCostsImpl<>("{2}{G}{G}"));
        ability.addTarget(new TargetPermanent(filter));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.getRightHalfCard().addAbility(ability);

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private TovolarsHuntmaster(final TovolarsHuntmaster card) {
        super(card);
    }

    @Override
    public TovolarsHuntmaster copy() {
        return new TovolarsHuntmaster(this);
    }
}

package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.WardAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EdeaPossessedSorceress extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature you control but don't own");

    static {
        filter.add(TargetController.NOT_YOU.getOwnerPredicate());
    }

    public EdeaPossessedSorceress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // At the beginning of combat on your turn, gain control of target creature an opponent controls until end of turn. Untap that creature. It gains haste until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new GainControlTargetEffect(Duration.EndOfTurn));
        ability.addEffect(new UntapTargetEffect("Untap that creature"));
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance()).withTargetDescription("It"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Whenever a creature you control but don't own dies, return it to the battlefield under its owner's control and you draw a card.
        ability = new DiesCreatureTriggeredAbility(
                new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false)
                        .setText("return it to the battlefield under its owner's control"),
                false, filter, true
        );
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and you"));
        this.addAbility(ability);
    }

    private EdeaPossessedSorceress(final EdeaPossessedSorceress card) {
        super(card);
    }

    @Override
    public EdeaPossessedSorceress copy() {
        return new EdeaPossessedSorceress(this);
    }
}

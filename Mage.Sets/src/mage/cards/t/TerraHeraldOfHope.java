package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TerraHeraldOfHope extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with power 3 or less from your graveyard");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 4));
    }

    public TerraHeraldOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trance -- At the beginning of combat on your turn, mill two cards. Terra gains flying until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new MillCardsControllerEffect(2));
        ability.addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn));
        this.addAbility(ability.withFlavorWord("Trance"));

        // Whenever Terra deals combat damage to a player, you may pay {2}. When you do, return target creature card with power 3 or less from your graveyard to the battlefield tapped.
        ReflexiveTriggeredAbility reflexiveAbility = new ReflexiveTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(true), false
        );
        reflexiveAbility.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DoWhenCostPaid(reflexiveAbility, new GenericManaCost(2), "Pay {2}?")
        ));
    }

    private TerraHeraldOfHope(final TerraHeraldOfHope card) {
        super(card);
    }

    @Override
    public TerraHeraldOfHope copy() {
        return new TerraHeraldOfHope(this);
    }
}

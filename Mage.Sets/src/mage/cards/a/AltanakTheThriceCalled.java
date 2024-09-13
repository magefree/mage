package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AltanakTheThriceCalled extends CardImpl {

    private static final FilterLandCard filter = new FilterLandCard("land card from your graveyard");

    public AltanakTheThriceCalled(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Altanak, the Thrice-Called becomes the target of a spell or ability an opponent controls, draw a card.
        this.addAbility(new BecomesTargetSourceTriggeredAbility(
                new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS
        ));

        // {1}{G}, Discard Altanak, the Thrice-Called: Return target land card from your graveyard to the battlefield tapped.
        Ability ability = new SimpleActivatedAbility(
                Zone.HAND, new ReturnFromGraveyardToBattlefieldTargetEffect(true), new ManaCostsImpl<>("{1}{G}")
        );
        ability.addCost(new DiscardSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private AltanakTheThriceCalled(final AltanakTheThriceCalled card) {
        super(card);
    }

    @Override
    public AltanakTheThriceCalled copy() {
        return new AltanakTheThriceCalled(this);
    }
}

package mage.sets.futuresight;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInGraveyard;

public class GraveScrabbler extends CardImpl {

    public GraveScrabbler(UUID ownerId) {
        super(ownerId, 86, "Grave Scrabbler", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "FUT";
        this.subtype.add("Zombie");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        //Madness {1}{B}
        this.addAbility(new MadnessAbility(this, new ManaCostsImpl("{1}{B}")));

        //When Grave Scrabbler enters the battlefield, if its madness cost was paid,
        //you may return target creature card from a graveyard to its owner's hand.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), true);
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card in a graveyard")));
        this.addAbility(new ConditionalTriggeredAbility(ability, MadnessAbility.GetCondition(),
                "When {this} enters the battlefield, if its madness cost was paid, you may return target creature card from a graveyard to its owner's hand."));
    }

    public GraveScrabbler(final GraveScrabbler card) {
        super(card);
    }

    @Override
    public Card copy() {
        return new GraveScrabbler(this);
    }

}

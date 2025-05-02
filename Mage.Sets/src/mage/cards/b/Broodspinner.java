package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.InsectBlackGreenFlyingToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Broodspinner extends CardImpl {

    public Broodspinner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Broodspinner enters, surveil 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(2)));

        // {4}{B}{G}, {T}, Sacrifice Broodspinner: Create a number of 1/1 black and green Insect creature tokens with flying equal to the number of card types among cards in your graveyard.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(
                new InsectBlackGreenFlyingToken(), CardTypesInGraveyardCount.YOU
        ).setText("create a number of 1/1 black and green Insect creature tokens with flying equal to the number of card types among cards in your graveyard"),
                new ManaCostsImpl<>("{4}{B}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability.addHint(CardTypesInGraveyardCount.YOU.getHint()));
    }

    private Broodspinner(final Broodspinner card) {
        super(card);
    }

    @Override
    public Broodspinner copy() {
        return new Broodspinner(this);
    }
}

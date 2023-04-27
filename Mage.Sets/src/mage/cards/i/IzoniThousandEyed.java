package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.game.permanent.token.IzoniInsectToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class IzoniThousandEyed extends CardImpl {

    public IzoniThousandEyed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}{G}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Undergrowth — When Izoni, Thousand-Eyed enters the battlefield, create a 1/1 black and green Insect creature token for each creature card in your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(
                        new IzoniInsectToken(),
                        new CardsInControllerGraveyardCount(
                                StaticFilters.FILTER_CARD_CREATURE
                        )
                ), false)
                .setAbilityWord(AbilityWord.UNDERGROWTH)
        );

        // {B}{G}, Sacrifice another creature: You gain 1 life and draw a card.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new GainLifeEffect(1),
                new ManaCostsImpl<>("{B}{G}")
        );
        ability.addEffect(
                new DrawCardSourceControllerEffect(1).setText("and draw a card")
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        )));
        this.addAbility(ability);
    }

    private IzoniThousandEyed(final IzoniThousandEyed card) {
        super(card);
    }

    @Override
    public IzoniThousandEyed copy() {
        return new IzoniThousandEyed(this);
    }
}

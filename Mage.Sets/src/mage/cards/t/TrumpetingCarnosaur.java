package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TrumpetingCarnosaur extends CardImpl {

    public TrumpetingCarnosaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Trumpeting Carnosaur enters the battlefield, discover 5.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscoverEffect(5)));

        // {2}{R}, Discard Trumpeting Carnosaur: It deals 3 damage to target creature or planeswalker.
        Ability ability = new SimpleActivatedAbility(
                Zone.HAND, new DamageTargetEffect(3, "It"),
                new ManaCostsImpl<>("{2}{R}")
        );
        ability.addCost(new DiscardSourceCost());
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);
    }

    private TrumpetingCarnosaur(final TrumpetingCarnosaur card) {
        super(card);
    }

    @Override
    public TrumpetingCarnosaur copy() {
        return new TrumpetingCarnosaur(this);
    }
}

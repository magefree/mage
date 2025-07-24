package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StegronTheDinosaurMan extends CardImpl {

    public StegronTheDinosaurMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Dinosaur Formula -- {1}{R}, Discard this card: Until end of turn, target creature you control gets +3/+1 and becomes a Dinosaur in addition to its other types.
        Ability ability = new SimpleActivatedAbility(
                Zone.HAND,
                new BoostTargetEffect(3, 1)
                        .setText("until end of turn, target creature you control gets +3/+1"),
                new ManaCostsImpl<>("{1}{R}")
        );
        ability.addCost(new DiscardSourceCost());
        ability.addEffect(new AddCardSubTypeTargetEffect(SubType.DINOSAUR, Duration.EndOfTurn)
                .setText("and becomes a Dinosaur in addition to its other types"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Dinosaur Formula"));
    }

    private StegronTheDinosaurMan(final StegronTheDinosaurMan card) {
        super(card);
    }

    @Override
    public StegronTheDinosaurMan copy() {
        return new StegronTheDinosaurMan(this);
    }
}

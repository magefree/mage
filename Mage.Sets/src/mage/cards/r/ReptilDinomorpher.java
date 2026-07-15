package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class ReptilDinomorpher extends CardImpl {

    public ReptilDinomorpher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Brontosaurus -- {3}: Until end of turn, Reptil becomes a Dinosaur Hero with base power and toughness 3/5 and gains vigilance and reach.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(3, 5, "Dinosaur Hero with base power and toughness 3/5 and gains vigilance and reach", SubType.DINOSAUR, SubType.HERO)
                    .withAbility(VigilanceAbility.getInstance())
                    .withAbility(ReachAbility.getInstance()),
                null, Duration.EndOfTurn
            ).withDurationRuleAtStart(true),
            new ManaCostsImpl<>("{3}")
        ).withFlavorWord("Brontosaurus"));

        // Tyrannosaurus Rex -- {6}: Until end of turn, Reptil becomes a Dinosaur Hero with base power and toughness 6/6 and gains trample.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(6, 6, "Dinosaur Hero with base power and toughness 6/6 and gains trample", SubType.DINOSAUR, SubType.HERO)
                    .withAbility(TrampleAbility.getInstance()),
                null, Duration.EndOfTurn
            ).withDurationRuleAtStart(true),
            new ManaCostsImpl<>("{6}")
        ).withFlavorWord("Tyrannosaurus Rex"));
    }

    private ReptilDinomorpher(final ReptilDinomorpher card) {
        super(card);
    }

    @Override
    public ReptilDinomorpher copy() {
        return new ReptilDinomorpher(this);
    }
}

package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author Codex
 */
public final class TrackhandTrainer extends CardImpl {

    public TrackhandTrainer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.MERFOLK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {3}{U}{U}: Draw a card.
        this.addAbility(new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{3}{U}{U}")));

        // Exhaust -- {U}: Conjure a card named Training Grounds onto the battlefield.
        this.addAbility(new ExhaustAbility(new ConjureCardEffect("Training Grounds", Zone.BATTLEFIELD, 1), new ManaCostsImpl<>("{U}")));
    }

    private TrackhandTrainer(final TrackhandTrainer card) {
        super(card);
    }

    @Override
    public TrackhandTrainer copy() {
        return new TrackhandTrainer(this);
    }
}

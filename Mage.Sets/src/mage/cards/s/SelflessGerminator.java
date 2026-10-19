package mage.cards.s;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Codex
 */
public final class SelflessGerminator extends CardImpl {

    public SelflessGerminator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Exhaust -- {1}: Conjure a card named Seed of Hope into your hand.
        this.addAbility(new ExhaustAbility(new ConjureCardEffect("Seed of Hope"), new ManaCostsImpl<>("{1}")));
    }

    private SelflessGerminator(final SelflessGerminator card) {
        super(card);
    }

    @Override
    public SelflessGerminator copy() {
        return new SelflessGerminator(this);
    }
}

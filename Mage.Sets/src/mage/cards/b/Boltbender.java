package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotNonTargetEffect;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetStackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Boltbender extends CardImpl {

    public Boltbender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Disguise {1}{R}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{1}{R}")));

        // When Boltbender is turned face up, you may choose new targets for any number of other spells and/or abilities.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new OneShotNonTargetEffect(
                new ChooseNewTargetsTargetEffect().setText("you may choose new targets for any number of other spells and/or abilities"),
                new TargetStackObject(0, Integer.MAX_VALUE, StaticFilters.FILTER_SPELL_OR_ABILITY))));
    }

    private Boltbender(final Boltbender card) {
        super(card);
    }

    @Override
    public Boltbender copy() {
        return new Boltbender(this);
    }
}

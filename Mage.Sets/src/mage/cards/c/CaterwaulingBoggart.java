package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CaterwaulingBoggart extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Goblins you control and Elementals");

    static {
        filter.add(Predicates.or(
                SubType.GOBLIN.getPredicate(),
                SubType.ELEMENTAL.getPredicate()
        ));
    }

    public CaterwaulingBoggart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Goblins you control and Elementals you control have menace.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.WhileOnBattlefield, filter
        )));
    }

    private CaterwaulingBoggart(final CaterwaulingBoggart card) {
        super(card);
    }

    @Override
    public CaterwaulingBoggart copy() {
        return new CaterwaulingBoggart(this);
    }
}

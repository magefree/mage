package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WardenOfEvosIsle extends CardImpl {

    private static final FilterCard filter = new FilterCard("Creature spells with flying");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public WardenOfEvosIsle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Creature spells with flying you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));

    }

    private WardenOfEvosIsle(final WardenOfEvosIsle card) {
        super(card);
    }

    @Override
    public WardenOfEvosIsle copy() {
        return new WardenOfEvosIsle(this);
    }
}

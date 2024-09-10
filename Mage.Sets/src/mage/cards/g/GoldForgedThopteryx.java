package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoldForgedThopteryx extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public GoldForgedThopteryx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W}{U}");

        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.THOPTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Each legendary permanent you control has ward {2}.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new WardAbility(new GenericManaCost(2)), Duration.WhileOnBattlefield, filter
        ).setText("each legendary permanent you control has ward {2}. <i>(Whenever it becomes the target " +
                "of a spell or ability an opponent controls, counter it unless that player pays {2})</i>")));
    }

    private GoldForgedThopteryx(final GoldForgedThopteryx card) {
        super(card);
    }

    @Override
    public GoldForgedThopteryx copy() {
        return new GoldForgedThopteryx(this);
    }
}

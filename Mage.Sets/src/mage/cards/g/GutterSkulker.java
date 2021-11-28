package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceAttackingAloneCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GutterSkulker extends CardImpl {

    public GutterSkulker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.g.GutterShortcut.class;

        // Gutter Skulker can't be blocked as long as it's attacking alone.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(Duration.WhileOnBattlefield),
                SourceAttackingAloneCondition.instance,
                "{this} can't be blocked as long as it's attacking alone"
        )));

        // Disturb {3}{U}
        this.addAbility(new DisturbAbility(this, "{3}{U}"));
    }

    private GutterSkulker(final GutterSkulker card) {
        super(card);
    }

    @Override
    public GutterSkulker copy() {
        return new GutterSkulker(this);
    }
}

package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAllSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TreeshakerChimera extends CardImpl {

    public TreeshakerChimera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.CHIMERA);
        this.power = new MageInt(8);
        this.toughness = new MageInt(5);

        // All creatures able to block Treeshaker Chimera do so.
        this.addAbility(new SimpleStaticAbility(new MustBeBlockedByAllSourceEffect()));

        // When Treeshaker Chimera dies, draw three cards.
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(3)));
    }

    private TreeshakerChimera(final TreeshakerChimera card) {
        super(card);
    }

    @Override
    public TreeshakerChimera copy() {
        return new TreeshakerChimera(this);
    }
}

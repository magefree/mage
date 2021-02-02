
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MonstrousCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Styxo
 */
public final class DagobahMawSlug extends CardImpl {

    public DagobahMawSlug(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}");
        this.subtype.add(SubType.SLUG);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // {3}{R}{G}{W}: Monstrosity 2.
        this.addAbility(new MonstrosityAbility("{3}{R}{G}{W}", 2));

        // As long as Dagobah Maw Slug is monstrous it has swampwalk.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new SwampwalkAbility()),
                MonstrousCondition.instance,
                "As long as Dagobah Maw Slug is monstrous it has swampwalk")
        ));
    }

    private DagobahMawSlug(final DagobahMawSlug card) {
        super(card);
    }

    @Override
    public DagobahMawSlug copy() {
        return new DagobahMawSlug(this);
    }
}

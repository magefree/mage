
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author North
 */
public final class EtchedChampion extends CardImpl {
    private static final String ruleText = "<i>Metalcraft</i> &mdash; Etched Champion has protection from all colors as long as you control three or more artifacts";

    private static final FilterCard filter = new FilterCard("all colors");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.BLACK),
                new ColorPredicate(ObjectColor.BLUE),
                new ColorPredicate(ObjectColor.GREEN),
                new ColorPredicate(ObjectColor.RED),
                new ColorPredicate(ObjectColor.WHITE)));
    }

    public EtchedChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        ContinuousEffect effect = new GainAbilitySourceEffect(new ProtectionAbility(filter), Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(effect, MetalcraftCondition.instance, ruleText)));
    }

    public EtchedChampion(final EtchedChampion card) {
        super(card);
    }

    @Override
    public EtchedChampion copy() {
        return new EtchedChampion(this);
    }
}

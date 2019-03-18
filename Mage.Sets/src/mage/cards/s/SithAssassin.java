
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.LifeLossOtherFromCombatWatcher;

/**
 *
 * @author Styxo
 */
public final class SithAssassin extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public SithAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.PUREBLOOD);
        this.subtype.add(SubType.SITH);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // <i>Hate</i> &mdash; When Sith Assassin enters the battlefield, if opponent lost life from source other than combat damage this turn, you may destroy target nonblack creature.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true),
                HateCondition.instance,
                "<i>Hate</i> &mdash; When {this} enters the battlefield, if an opponent lost life from a source other than combat damage this turn, you may destroy target nonblack creature.");
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability, new LifeLossOtherFromCombatWatcher());
    }

    public SithAssassin(final SithAssassin card) {
        super(card);
    }

    @Override
    public SithAssassin copy() {
        return new SithAssassin(this);
    }
}

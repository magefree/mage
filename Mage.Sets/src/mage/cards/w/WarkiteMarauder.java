
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesTargetEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class WarkiteMarauder extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(new DefendingPlayerControlsPredicate());
    }

    public WarkiteMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Warkite Marauder attacks, target creature defending player controls loses all abilities and has base power and toughness 0/1 until end of turn.
        Ability ability = new AttacksTriggeredAbility(new LoseAllAbilitiesTargetEffect(Duration.EndOfTurn)
                .setText("target creature defending player controls loses all abilities"), false);
        ability.addEffect(new SetPowerToughnessTargetEffect(0, 1, Duration.EndOfTurn)
                .setText("and has base power and toughness 0/1 until end of turn"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

    }

    public WarkiteMarauder(final WarkiteMarauder card) {
        super(card);
    }

    @Override
    public WarkiteMarauder copy() {
        return new WarkiteMarauder(this);
    }
}

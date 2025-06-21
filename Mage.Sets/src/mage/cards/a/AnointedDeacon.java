package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author caldover
 */
public final class AnointedDeacon extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Vampire");

    static {
        filter.add(SubType.VAMPIRE.getPredicate());
    }

    public AnointedDeacon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, you may have target Vampire get +2/+0 until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new BoostTargetEffect(2, 0, Duration.EndOfTurn), true);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private AnointedDeacon(final AnointedDeacon card) {
        super(card);
    }

    @Override
    public AnointedDeacon copy() {
        return new AnointedDeacon(this);
    }
}

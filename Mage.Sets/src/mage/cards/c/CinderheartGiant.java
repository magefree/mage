package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class CinderheartGiant extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public CinderheartGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Cinderheart Giant dies, it deals 7 damage to a creature an opponent controls chosen at random.
        Ability ability = new DiesSourceTriggeredAbility(
                new DamageTargetEffect(7).setText("it deals 7 damage to a creature an opponent controls chosen at random"));
        Target target = new TargetCreaturePermanent(1, 1, filter, true);
        target.setRandom(true);
        ability.addTarget(target);

        this.addAbility(ability);
    }

    private CinderheartGiant(final CinderheartGiant card) {
        super(card);
    }

    @Override
    public CinderheartGiant copy() {
        return new CinderheartGiant(this);
    }
}

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Loki
 */
public final class AgrusKosWojekVeteran extends CardImpl {

    private static final FilterAttackingCreature filterRed = new FilterAttackingCreature("attacking red creatures");
    private static final FilterAttackingCreature filterWhite = new FilterAttackingCreature("attacking white creatures");

    static {
        filterRed.add(new ColorPredicate(ObjectColor.RED));
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public AgrusKosWojekVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);


        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        // Whenever Agrus Kos, Wojek Veteran attacks, attacking red creatures get +2/+0 and attacking white creatures get +0/+2 until end of turn.
        Ability ability = new AttacksTriggeredAbility(new BoostAllEffect(2, 0, Duration.EndOfTurn, filterRed, false), false);
        ability.addEffect(new BoostAllEffect(0, 2, Duration.EndOfTurn, filterWhite, false).concatBy("and"));
        this.addAbility(ability);
    }

    private AgrusKosWojekVeteran(final AgrusKosWojekVeteran card) {
        super(card);
    }

    @Override
    public AgrusKosWojekVeteran copy() {
        return new AgrusKosWojekVeteran(this);
    }
}

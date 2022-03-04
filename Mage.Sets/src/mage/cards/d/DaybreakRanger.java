package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class DaybreakRanger extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public DaybreakRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.subtype.add(SubType.RANGER);
        this.subtype.add(SubType.WEREWOLF);

        this.secondSideCardClazz = mage.cards.n.NightfallPredator.class;

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Daybreak Ranger deals 2 damage to target creature with flying.
        Ability activatedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new TapSourceCost());
        activatedAbility.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(activatedAbility);
        // At the beginning of each upkeep, if no spells were cast last turn, transform Daybreak Ranger.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private DaybreakRanger(final DaybreakRanger card) {
        super(card);
    }

    @Override
    public DaybreakRanger copy() {
        return new DaybreakRanger(this);
    }
}
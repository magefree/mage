package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author North
 */
public final class HagraDiabolist extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Allies you control");

    static {
        filter.add(SubType.ALLY.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public HagraDiabolist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.SHAMAN);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        Ability ability = new AllyEntersBattlefieldTriggeredAbility(new LoseLifeTargetEffect(new PermanentsOnBattlefieldCount(filter)).setText("you may have target player lose life equal to the number of Allies you control"), true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability.setAbilityWord(null));
    }

    private HagraDiabolist(final HagraDiabolist card) {
        super(card);
    }

    @Override
    public HagraDiabolist copy() {
        return new HagraDiabolist(this);
    }
}

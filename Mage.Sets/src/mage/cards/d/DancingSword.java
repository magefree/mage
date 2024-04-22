package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DancingSword extends CardImpl {

    public DancingSword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 1)));

        // When equipped creature dies, you may have Dancing Sword become a 2/1 Construct artifact creature with flying and ward {1}. If you do, it isn't an Equipment.
        this.addAbility(new DiesAttachedTriggeredAbility(
                new BecomesCreatureSourceEffect(new DancingSwordToken(), CardType.ARTIFACT, Duration.WhileOnBattlefield).andNotEquipment(true), "equipped creature", true
        ).setTriggerPhrase("When equipped creature dies, "));

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1), new TargetControlledCreaturePermanent(), false));
    }

    private DancingSword(final DancingSword card) {
        super(card);
    }

    @Override
    public DancingSword copy() {
        return new DancingSword(this);
    }
}

class DancingSwordToken extends TokenImpl {

    public DancingSwordToken() {
        super("", "2/1 Construct artifact creature with flying and ward {1}. If you do, it isn't an Equipment");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CONSTRUCT);
        power = new MageInt(2);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new WardAbility(new GenericManaCost(1), false));
    }

    private DancingSwordToken(final DancingSwordToken token) {
        super(token);
    }

    public DancingSwordToken copy() {
        return new DancingSwordToken(this);
    }

}

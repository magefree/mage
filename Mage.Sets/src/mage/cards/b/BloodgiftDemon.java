package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author North
 */
public final class BloodgiftDemon extends CardImpl {

    public BloodgiftDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, target player draws a card and loses 1 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DrawCardTargetEffect(1));
        ability.addEffect(new LoseLifeTargetEffect(1).setText("and loses 1 life"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private BloodgiftDemon(final BloodgiftDemon card) {
        super(card);
    }

    @Override
    public BloodgiftDemon copy() {
        return new BloodgiftDemon(this);
    }
}

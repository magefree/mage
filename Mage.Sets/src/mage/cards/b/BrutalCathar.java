package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TransformsOrEntersTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrutalCathar extends CardImpl {

    public BrutalCathar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.secondSideCardClazz = mage.cards.m.MoonrageBrute.class;

        // When this creature enters the battlefield or transforms into Brutal Cathar, exile target creature an opponent controls until this creature leaves the battlefield.
        Ability ability = new TransformsOrEntersTriggeredAbility(
                new ExileUntilSourceLeavesEffect(), false
        ).setTriggerPhrase("When this creature enters the battlefield or transforms into {this}, ");
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Daybound
        this.addAbility(new DayboundAbility());
    }

    private BrutalCathar(final BrutalCathar card) {
        super(card);
    }

    @Override
    public BrutalCathar copy() {
        return new BrutalCathar(this);
    }
}

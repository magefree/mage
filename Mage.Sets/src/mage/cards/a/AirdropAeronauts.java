package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class AirdropAeronauts extends CardImpl {

    public AirdropAeronauts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // <i>Revolt</i> &mdash; When Airdrop Aeronauts enters the battlefield, if a permanent you controlled left the battlefield this turn, you gain 5 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(5))
                .withInterveningIf(RevoltCondition.instance)
                .setAbilityWord(AbilityWord.REVOLT)
                .addHint(RevoltCondition.getHint()), new RevoltWatcher());
    }

    private AirdropAeronauts(final AirdropAeronauts card) {
        super(card);
    }

    @Override
    public AirdropAeronauts copy() {
        return new AirdropAeronauts(this);
    }
}

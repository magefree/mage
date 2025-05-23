package mage.cards.h;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.effects.mana.BasicManaEffect;
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
public final class HiddenHerbalists extends CardImpl {

    public HiddenHerbalists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Revolt</i> &mdash When Hidden Herbalists enters the battlefield, if a permanent you controlled left the battlefield this turn, add {G}{G};
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BasicManaEffect(Mana.GreenMana(2)))
                .withInterveningIf(RevoltCondition.instance)
                .setAbilityWord(AbilityWord.REVOLT)
                .addHint(RevoltCondition.getHint()), new RevoltWatcher());
    }

    private HiddenHerbalists(final HiddenHerbalists card) {
        super(card);
    }

    @Override
    public HiddenHerbalists copy() {
        return new HiddenHerbalists(this);
    }
}

package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Ninja11Token;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FootMystic extends CardImpl {

    public FootMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Disappear -- When this creature enters, if a permanent left the battlefield under your control this turn, create a 1/1 black Ninja creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new Ninja11Token()))
                .withInterveningIf(RevoltCondition.instance)
                .addHint(RevoltCondition.getHint())
                .setAbilityWord(AbilityWord.DISAPPEAR), new RevoltWatcher());
    }

    private FootMystic(final FootMystic card) {
        super(card);
    }

    @Override
    public FootMystic copy() {
        return new FootMystic(this);
    }
}

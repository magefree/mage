
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.watchers.common.RevoltWatcher;

/**
 *
 * @author fireshoes
 */
public final class VengefulRebel extends CardImpl {

    public VengefulRebel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.AETHERBORN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // <i>Revolt</i> &mdash; When Vengeful Rebel enters the battlefield, if a permanent you controlled left the battlefield this turn,
        // target creature an opponent controls gets -3/-3 until end of turn.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-3, -3, Duration.EndOfTurn), false),
                RevoltCondition.instance,
                "When {this} enters the battlefield, if a permanent you controlled left the battlefield this turn, "
                + "target creature an opponent controls gets -3/-3 until end of turn"
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        ability.setAbilityWord(AbilityWord.REVOLT);
        this.addAbility(ability, new RevoltWatcher());
    }

    private VengefulRebel(final VengefulRebel card) {
        super(card);
    }

    @Override
    public VengefulRebel copy() {
        return new VengefulRebel(this);
    }
}

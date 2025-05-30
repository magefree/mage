
package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
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
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-3, -3))
                .withInterveningIf(RevoltCondition.instance);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        ability.setAbilityWord(AbilityWord.REVOLT);
        this.addAbility(ability.addHint(RevoltCondition.getHint()), new RevoltWatcher());
    }

    private VengefulRebel(final VengefulRebel card) {
        super(card);
    }

    @Override
    public VengefulRebel copy() {
        return new VengefulRebel(this);
    }
}

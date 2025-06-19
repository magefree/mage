package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;
import mage.watchers.common.LifeLossOtherFromCombatWatcher;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class SithInquisitor extends CardImpl {

    public SithInquisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SITH);
        this.power = new MageInt(5);
        this.toughness = new MageInt(1);

        // <i>Hate</i> &mdash; When Sith Assassin enters the battlefield, if opponent lost life from source other than combat damage this turn, target player discard a card at random.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(1, true)).withInterveningIf(HateCondition.instance);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability.setAbilityWord(AbilityWord.HATE), new LifeLossOtherFromCombatWatcher());
    }

    private SithInquisitor(final SithInquisitor card) {
        super(card);
    }

    @Override
    public SithInquisitor copy() {
        return new SithInquisitor(this);
    }
}

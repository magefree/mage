package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.LifeLossOtherFromCombatWatcher;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK;

/**
 * @author Styxo
 */
public final class SithAssassin extends CardImpl {

    public SithAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.PUREBLOOD);
        this.subtype.add(SubType.SITH);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // <i>Hate</i> &mdash; When Sith Assassin enters the battlefield, if opponent lost life from source other than combat damage this turn, you may destroy target nonblack creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true).withInterveningIf(HateCondition.instance);
        ability.addTarget(new TargetPermanent(FILTER_PERMANENT_CREATURE_NON_BLACK));
        this.addAbility(ability.setAbilityWord(AbilityWord.HATE), new LifeLossOtherFromCombatWatcher());
    }

    private SithAssassin(final SithAssassin card) {
        super(card);
    }

    @Override
    public SithAssassin copy() {
        return new SithAssassin(this);
    }
}

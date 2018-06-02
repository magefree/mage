
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.watchers.common.PlayerAttackedWatcher;

/**
 *
 * @author LevelX2
 */
public final class SirenReaver extends CardImpl {

    public SirenReaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SIREN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // <i>Raid</i> — Siren Reaver costs {1} less to cast if you attacked with a creature this turn.
        Ability ability = new SimpleStaticAbility(Zone.STACK, new SpellCostReductionSourceEffect(1, RaidCondition.instance));
        ability.setAbilityWord(AbilityWord.RAID);
        ability.setRuleAtTheTop(true);
        this.addAbility(ability, new PlayerAttackedWatcher());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

    }

    public SirenReaver(final SirenReaver card) {
        super(card);
    }

    @Override
    public SirenReaver copy() {
        return new SirenReaver(this);
    }
}

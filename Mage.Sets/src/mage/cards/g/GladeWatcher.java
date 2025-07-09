package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GladeWatcher extends CardImpl {

    public GladeWatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // <i>Formidable</i> &mdash; {G}: Glade Watcher can attack this turn as though it didn't have defender. Activate this ability only if creatures you control have total power 8 or greater.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.EndOfTurn),
                new ManaCostsImpl<>("{G}"), FormidableCondition.instance
        ).setAbilityWord(AbilityWord.FORMIDABLE));
    }

    private GladeWatcher(final GladeWatcher card) {
        super(card);
    }

    @Override
    public GladeWatcher copy() {
        return new GladeWatcher(this);
    }
}

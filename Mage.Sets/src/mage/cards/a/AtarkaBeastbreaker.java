package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
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
public final class AtarkaBeastbreaker extends CardImpl {

    public AtarkaBeastbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Formidable</i> &mdash; {4}{G}: Atarka Beastbreaker gets +4/+4 until end of turn. Activate this only if creatures you control have total power 8 or greater.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new BoostSourceEffect(4, 4, Duration.EndOfTurn),
                new ManaCostsImpl<>("{4}{G}"), FormidableCondition.instance
        ).setAbilityWord(AbilityWord.FORMIDABLE));
    }

    private AtarkaBeastbreaker(final AtarkaBeastbreaker card) {
        super(card);
    }

    @Override
    public AtarkaBeastbreaker copy() {
        return new AtarkaBeastbreaker(this);
    }
}

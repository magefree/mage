package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LurkingArynx extends CardImpl {

    public LurkingArynx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // <i>Formidable</i> &mdash; {2}{G}: Target creature blocks Lurking Arynx this turn if able. Activate this ability only if creatures you control have total power 8 or greater.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new MustBeBlockedByTargetSourceEffect(Duration.EndOfTurn),
                new ManaCostsImpl<>("{2}{G}"), FormidableCondition.instance
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.setAbilityWord(AbilityWord.FORMIDABLE));
    }

    private LurkingArynx(final LurkingArynx card) {
        super(card);
    }

    @Override
    public LurkingArynx copy() {
        return new LurkingArynx(this);
    }
}

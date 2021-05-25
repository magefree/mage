package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class DuskFeaster extends CardImpl {

    public DuskFeaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // <i>Delirium</i> &mdash; This spell costs {2} less to cast if there are four or more card types among cards in your graveyard.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(2, DeliriumCondition.instance));
        ability.setRuleAtTheTop(true);
        ability.setAbilityWord(AbilityWord.DELIRIUM);
        ability.addHint(CardTypesInGraveyardHint.YOU);
        this.addAbility(ability);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private DuskFeaster(final DuskFeaster card) {
        super(card);
    }

    @Override
    public DuskFeaster copy() {
        return new DuskFeaster(this);
    }
}
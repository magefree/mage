package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ChromaCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.ManaType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class OutrageShaman extends CardImpl {

    public OutrageShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Chroma - When Outrage Shaman enters the battlefield, it deals damage to target creature equal to the number of red mana symbols in the mana costs of permanents you control.
        DynamicValue xValue = new ChromaCount(ManaType.RED);
        Effect effect = new DamageTargetEffect(xValue);
        effect.setText("it deals damage to target creature equal to the number of red mana symbols in the mana costs of permanents you control");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        ability.addTarget(new TargetCreaturePermanent());
        ability.setAbilityWord(AbilityWord.CHROMA);
        ability.addHint(new ValueHint("Red mana symbols in your permanents", xValue));
        this.addAbility(ability);

    }

    private OutrageShaman(final OutrageShaman card) {
        super(card);
    }

    @Override
    public OutrageShaman copy() {
        return new OutrageShaman(this);
    }
}


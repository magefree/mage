package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.condition.common.CastAnotherSpellThisTurnCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CopySourceSpellEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Jmlundeen
 */
public final class SageOfTheSkies extends CardImpl {

    public SageOfTheSkies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When you cast this spell, if you've cast another spell this turn, copy this spell.
        OneShotEffect effect = new CopySourceSpellEffect().setText("copy this spell. <i>(The copy becomes a token.)</i>");
        this.addAbility(new CastSourceTriggeredAbility(effect)
                .withInterveningIf(CastAnotherSpellThisTurnCondition.instance)
                .addHint(CastAnotherSpellThisTurnCondition.instance.getHint())
        );

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

    }

    private SageOfTheSkies(final SageOfTheSkies card) {
        super(card);
    }

    @Override
    public SageOfTheSkies copy() {
        return new SageOfTheSkies(this);
    }
}

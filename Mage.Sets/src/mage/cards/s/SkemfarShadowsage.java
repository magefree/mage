package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.GreatestSharedCreatureTypeCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkemfarShadowsage extends CardImpl {

    public SkemfarShadowsage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // When Skemfar Shadowsage enters the battlefield, choose one —
        // • Each opponent loses X life, where X is the greatest number of creatures you control that have a creature type in common.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeOpponentsEffect(
                GreatestSharedCreatureTypeCount.instance
        ).setText("each opponent loses X life, where X is the greatest number " +
                "of creatures you control that have a creature type in common"));

        // • You gain X life, where X is the greatest number of creatures you control that have a creature type in common.
        ability.addMode(new Mode(new GainLifeEffect(
                GreatestSharedCreatureTypeCount.instance
        ).setText("you gain X life, where X is the greatest number " +
                "of creatures you control that have a creature type in common")));

        this.addAbility(ability.addHint(GreatestSharedCreatureTypeCount.getHint()));
    }

    private SkemfarShadowsage(final SkemfarShadowsage card) {
        super(card);
    }

    @Override
    public SkemfarShadowsage copy() {
        return new SkemfarShadowsage(this);
    }
}

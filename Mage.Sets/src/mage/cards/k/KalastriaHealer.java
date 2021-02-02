
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class KalastriaHealer extends CardImpl {

    public KalastriaHealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // <i>Rally</i> &mdash; Whenever Kalastria Healer or another Ally enters the battlefield under your control, each opponent loses 1 life and you gain 1 life.
        Ability ability = new AllyEntersBattlefieldTriggeredAbility(new LoseLifeOpponentsEffect(1), false);
        Effect effect = new GainLifeEffect(1);
        effect.setText("and you gain 1 life");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private KalastriaHealer(final KalastriaHealer card) {
        super(card);
    }

    @Override
    public KalastriaHealer copy() {
        return new KalastriaHealer(this);
    }
}

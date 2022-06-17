
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class SokenzanSpellblade extends CardImpl {

    public SokenzanSpellblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.SAMURAI);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Bushido 1
        this.addAbility(new BushidoAbility(1));
        // {1}{R}: Sokenzan Spellblade gets +X/+0 until end of turn, where X is the number of cards in your hand.
        Effect effect = new BoostSourceEffect(CardsInControllerHandCount.instance, StaticValue.get(0), Duration.EndOfTurn, true);
        effect.setText("{this} gets +X/+0 until end of turn, where X is the number of cards in your hand");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                effect, new ManaCostsImpl<>("{1}{R}")
        ));
    }

    private SokenzanSpellblade(final SokenzanSpellblade card) {
        super(card);
    }

    @Override
    public SokenzanSpellblade copy() {
        return new SokenzanSpellblade(this);
    }
}

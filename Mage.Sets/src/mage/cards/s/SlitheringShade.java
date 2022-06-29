
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Styxo
 */
public final class SlitheringShade extends CardImpl {

    public SlitheringShade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.SHADE);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        //Defender
        this.addAbility(DefenderAbility.getInstance());

        //{B}: Slithering Shade gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{B}")));

        // Hellbent — Slithering Shade can attack as though it didn't have defender as long as you have no cards in hand.
        Effect effect = new ConditionalAsThoughEffect(
                new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield),
                HellbentCondition.instance);
        effect.setText("<i>Hellbent</i> &mdash; {this} can attack as though it didn't have defender as long as you have no cards in hand");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

    }

    private SlitheringShade(final SlitheringShade card) {
        super(card);
    }

    @Override
    public SlitheringShade copy() {
        return new SlitheringShade(this);
    }
}

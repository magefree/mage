
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Styxo
 */
public final class SarlaccPit extends CardImpl {

    public SarlaccPit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // {R}{G}, Sacrifice a land: Monstrosity 1.
        Ability ability = new MonstrosityAbility("{R}{G}", 1);
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent("a land"))));
        this.addAbility(ability);

        // When Sarlacc Pit becomes monstrous, it loses hexproof and gains first strike and deathtouch.
        ability = new BecomesMonstrousSourceTriggeredAbility(new LoseAbilitySourceEffect(HexproofAbility.getInstance()));
        Effect effect = new GainAbilitySourceEffect(FirstStrikeAbility.getInstance());
        effect.setText("and gains first strike");
        ability.addEffect(effect);
        effect = new GainAbilitySourceEffect(DeathtouchAbility.getInstance());
        effect.setText("and deathtouch");
        ability.addEffect(effect);

        this.addAbility(ability);
    }

    private SarlaccPit(final SarlaccPit card) {
        super(card);
    }

    @Override
    public SarlaccPit copy() {
        return new SarlaccPit(this);
    }
}

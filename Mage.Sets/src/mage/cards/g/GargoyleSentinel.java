package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class GargoyleSentinel extends CardImpl {

    public GargoyleSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(DefenderAbility.getInstance());

        // {3}: Until end of turn, Gargoyle Sentinel loses defender and gains flying.
        Effect effect = new LoseAbilitySourceEffect(DefenderAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("until end of turn, {this} loses defender");
        Ability ability = new SimpleActivatedAbility(effect, new GenericManaCost(3));
        effect = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains flying");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private GargoyleSentinel(final GargoyleSentinel card) {
        super(card);
    }

    @Override
    public GargoyleSentinel copy() {
        return new GargoyleSentinel(this);
    }
}
